package com.hrm.document.handler;

import com.hrm.commons.beans.Document;
import com.hrm.commons.beans.User;
import com.hrm.document.service.IDocumentService;
import com.hrm.utils.PageModel;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

@Controller
@RequestMapping("/document")
public class DocumentHandler {
    @Autowired
    private IDocumentService documentService;

    //文件查询
    @RequestMapping("/selectDocument")
    public String selectDocument(@RequestParam(defaultValue = "1") Integer pageIndex, Model model, String title){
        PageModel pageModel = new PageModel();
        int count = documentService.selectDocumentCount(title);
        pageModel.setPageIndex(pageIndex);
        pageModel.setRecordCount(count);

        List<Document> documents = documentService.selectDocument(title,pageModel);
        for(Document d: documents){
            System.out.println("d的值是：---" + d);
        }
        model.addAttribute("title",title);
        model.addAttribute("pageModel",pageModel);
        model.addAttribute("documents",documents);
        return "/jsp/document/document.jsp";
    }

    //文件上传
    @RequestMapping("/addDocument")
    public String addDocument(Document document, Model model, HttpSession session) throws IOException {
        //1.将文档存入服务器
        String path = "F:/1-hrm-upload";/*文件存储的路径*/
        //获取目录对应的文件
        File file = new File(path);
        //判断文件的路径是否存在，若不存在则新建目录
        if(!file.exists()){
            file.mkdirs();
        }
        //获取文件原始名称，前面通过UUID或currentTimeMills（）使文件名唯一，防止文件重名而被覆盖
        String fileName = System.currentTimeMillis() + "-" + document.getFile().getOriginalFilename();
        //获取文件，并将文件类型从MultipartFile转换为File类型，然后将文件保存到path对应的目录
        document.getFile().transferTo(new File(path,fileName));

        //2.将文档信息存入数据库
        //为User属性赋值
        User login_user = (User)session.getAttribute("login_user");
        document.setUser(login_user);
        //为filename赋值
        document.setFileName(fileName);
        int row = documentService.addDocument(document);
        if(row>0){
            PageModel pageModel = new PageModel();
            pageModel.setRecordCount(documentService.selectDocumentCount(null));
            return "redirect:/document/selectDocument?pageIndex="+pageModel.getTotalSize();
        }else {
            model.addAttribute("fail","文档上传失败");
            return "/jsp/fail.jsp";
        }
    }

    //删除文档-两方面的删除
    @RequestMapping("/removeDocument")
    @ResponseBody
    public String removeDocument(Integer[] ids,Model model){
        int rows = 0;
        for(Integer id:ids){
            Document document = documentService.findDocumentById(id);
            //删除本地磁盘文件
            String path = "F:/1-hrm-upload";
            File file = new File(path,document.getFileName());
            if(file.exists()){
                file.delete();
            }
            //删除数据库信息
            int i = documentService.deleteDocument(id);
            rows = rows + i;
        }
        if(rows==ids.length){
            return "OK";
        }else{
            return "FAIL";
        }
    }

    //修改文档
    @RequestMapping("/updateDocument")
    public String updateDocument(String flag,Model model,Document document,Integer pageIndex) throws IOException {
        //获取原始文档信息
        Document oldDocument = documentService.findDocumentById(document.getId());
        //flag标记当前处于哪一步
        if(flag == null){
            model.addAttribute("document",oldDocument);
            model.addAttribute("pageIndex",pageIndex);
            return "/jsp/document/showUpdateDocument.jsp";
        }else{
            //若修改后存在需要上传的新文档，则删除原始文档
            if(!document.getFile().isEmpty()){
                //获取本地磁盘路径
                String path = "F:/1-hrm-upload";
                File file = new File(path,oldDocument.getFileName());
                if(file.exists()){
                    file.delete();
                }
                //将新文档存入本地磁盘
                String fileName = System.currentTimeMillis() + "-" + document.getFile().getOriginalFilename();
                document.getFile().transferTo(new File(path,fileName));
                document.setFileName(fileName);
            }
            //修改数据库中该文档的信息
            int row = documentService.updateDocument(document);
            if (row>0){
                return "redirect:/document/selectDocument?pageIndex="+pageIndex;
            }else{
                model.addAttribute("fail","文档修改失败！");
                return "/jsp/fail.jsp";
            }
        }
    }

    //下载文档-返回值类型为ResponseEntity，响应实体，以字节流方式进行返回
    @RequestMapping("/downLoad")
    public ResponseEntity<Object> downLoad(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String path = "F:/1-hrm-upload";
        Document document = documentService.findDocumentById(id);
        String fileName = document.getFileName();
        //获取所要下载的文件
        File file = new File(path,fileName);
        //判断文件是否存在
        if(file.exists()){
            //解决编码问题
            fileName = processFileName(request,fileName);
            //setFileDownloadHeader(request,response,fileName);
            //设置响应头的响应内容类型
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment",fileName);
            return new ResponseEntity<Object>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        }else {
            String error = "文件不存在！<input type=\"button\" onclick=\"JavaScript:history.back(-1);\" value\"返回\"></input>";
            MediaType mediaType = new MediaType("text","HTML", Charset.forName("UTF8"));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            return new ResponseEntity<Object>(error,headers,HttpStatus.OK);
        }
    }

    //解决IE，Chrome，Firefox文件下载乱码问题
    public String processFileName(HttpServletRequest request,String fileNames){
        String codeFileName = null;
        try{
            //获取浏览器标识
            String agent = request.getHeader("USER-AGENT");
            if(null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")){
                //IE
                String name = java.net.URLEncoder.encode(fileNames,"UTF8");
                codeFileName = name;
            } else if(null != agent && -1 != agent.indexOf("Mozilla")){
                //火狐，Chrome等
                codeFileName = new String(fileNames.getBytes("UTF-8"),"iso-8859-1");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return codeFileName;
    }
}

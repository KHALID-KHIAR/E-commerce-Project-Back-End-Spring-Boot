package com.kh.EcomercebackEndspringboot.Service;

import com.kh.EcomercebackEndspringboot.Config.FreeMakerConfig;
import com.kh.EcomercebackEndspringboot.Entity.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailService {

    @Value("${verification_code_length}")
    private  int code_length  ;
    @Value("${spring.mail.username}")
    private  String emailSender;
    private final String caracters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final JavaMailSender mailSender;
    @Autowired
    private Configuration configuration ;
    public EmailService(JavaMailSender mailSender){
        this.mailSender=mailSender;
    }
    public void sendVerificationMail(User user,String code) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage= mailSender.createMimeMessage() ;
        MimeMessageHelper mHelper=new MimeMessageHelper(mimeMessage);

        Map<String,String> dataModel = new HashMap<>();
        dataModel.put("link","http://localhost:9090/user/verify?email="+user.getEmail()+"&code="+code);
        dataModel.put("firstname",user.getFirstname());
        Template template = configuration.getTemplate("Email_Verification.ftl");
        // maybe add the url of the frontend here(angular4200/...) and in angular make a get request to /verify..
        String template_toString = FreeMarkerTemplateUtils.processTemplateIntoString(template,dataModel);


        mHelper.setSubject("Please Verify your E-mail !");
        mHelper.setTo(user.getEmail());
        mHelper.setFrom(emailSender);
        mHelper.setText(template_toString,true);

        mailSender.send(mimeMessage);
    }

    public String generateVerificationCode(){
        Random rd = new Random();
        StringBuilder code = new StringBuilder(code_length) ;
        for (int i=0 ; i<code_length;i++){
            code.append( caracters.charAt(rd.nextInt(caracters.length())) );
        }
        return code.toString();
    }
}

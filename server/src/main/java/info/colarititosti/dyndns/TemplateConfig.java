package info.colarititosti.dyndns;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TemplateConfig {

    public static void saveFilledTemplates(String ip){
        try {
            String currentPath = new java.io.File( "." ).getCanonicalPath();
            List<String> templates = getTemplates(currentPath);
            for (String s : templates) {
                System.out.println(s);
                Path path = Paths.get(s);
                Charset charset = StandardCharsets.UTF_8;
                String content = new String(Files.readAllBytes(path), charset);
                content = content.replaceAll("\\[(.*?)\\]", ip);
                if (s.contains("nginx.conf")){
                    Files.write(Paths.get("/etc/nginx/".concat(s)), content.getBytes(charset));
                } else {
                    Files.write(Paths.get("/etc/nginx/conf.d/".concat(s)), content.getBytes(charset));
                }



            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getTemplates(String doneOutput) {
        List<String> result = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(doneOutput))) {
            result = walk.filter(Files::isRegularFile).filter(f -> f.getFileName().toString().endsWith("conf"))
                    .map(f -> f.getFileName().toString()).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

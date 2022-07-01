package ua.nure.andrii.yahniukov.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    @Value("${db.name}")
    private String dbName;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;

    public void backup()
            throws IOException, InterruptedException {
        File outputFile = new File("C:\\Users\\andri\\OneDrive\\Desktop\\Andrii\\University\\3_course\\2_semester\\APZ\\electro\\electrobackend\\src\\main\\resources\\db\\backups", "backup.sql");
        String sqlCommandBackup = String.format("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump -u%s -p%s --add-drop-table --databases %s -r %s",
                dbUsername, dbPassword, dbName, outputFile);
        Process process = Runtime.getRuntime().exec(sqlCommandBackup);
        process.waitFor();
    }

    public void restore()
            throws IOException, InterruptedException {
        String sourceFile = "C:\\Users\\andri\\OneDrive\\Desktop\\Andrii\\University\\3_course\\2_semester\\APZ\\electro\\electrobackend\\src\\main\\resources\\db\\backups\\backup.sql";
        String[] sqlCommandRestore = new String[]{
                "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql",
                "-u" + dbUsername,
                "-p" + dbPassword,
                "-e",
                " source " + sourceFile,
                dbName
        };
        Process process = Runtime.getRuntime().exec(sqlCommandRestore);
        process.waitFor();
    }
}
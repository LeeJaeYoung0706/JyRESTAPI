package rest.JyRESTAPI.utils.https;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class CliEncryptor implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // 암호화 실행용 인자가 없으면 무시 (서버 실행 때 영향 없음)
        if (args.length == 0) return;

        String master = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");
        if (master == null || master.isBlank()) {
            throw new IllegalStateException("Set JASYPT_ENCRYPTOR_PASSWORD env var");
        }

        StandardPBEStringEncryptor enc = new StandardPBEStringEncryptor();
        enc.setPassword(master);
        enc.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        enc.setIvGenerator(new RandomIvGenerator());
        String encrypted = enc.encrypt(args[0]);
        System.out.println("ENC(" + encrypted + ")");

        System.exit(0); // 한 번만 실행 후 종료
    }
}
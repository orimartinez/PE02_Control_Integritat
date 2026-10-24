import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Hash01 {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
            throws NoSuchAlgorithmException, IOException {

        boolean seguirMenu = true;

        while (seguirMenu) {
            System.out.println("=======SELECCIONA UNA OPCIÓ=======");
            System.out.println("1. Càlcul de resums");
            System.out.println("2. Verificació de l'integritat dels missatges");
            System.out.println("3. Registre d'usuaris amb salt");
            System.out.println("4. Inici de sessió i verificació de contrasenyes");
            System.out.println("5. Proves de la pràctica");
            System.out.println("6. Sortir del menú");
            System.out.println("==================================");
            int opciomenu = llegirEnter();

            switch (opciomenu) {
                case 1:
                    ex1();
                    break;
                case 2:
                    ex2();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    seguirMenu = sortirMenu();
                    break;

                default:
                    System.out.println("Error, torna-ho a intentar!");
                    break;
            }
        }

        sc.close();
    }

    public static void ex1() throws NoSuchAlgorithmException {

        String textIn = "";

        System.out.print("Entra text: ");

        while (textIn.isEmpty()) {
            textIn = sc.nextLine();
        }

        // Convertim l'string a un array de bytes
        byte[] bytesIn = textIn.getBytes(StandardCharsets.UTF_8);

        // Instanciem la classe per fer el hash
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

        // Calculem el hash
        byte[] hashResult = sha256.digest(bytesIn);

        // Convertim el hash a hexadecimal per mostrar-lo
        String textOut = HexFormat.of().withUpperCase()
                .formatHex(hashResult);

        System.out.println("Hash: " + textOut);

        // Tornem a calcular el hash per saber si es mante
        byte[] hashRepetit = sha256.digest(bytesIn);

        // Ho convertim a hexadecimal
        String hashRepetitHex = HexFormat.of().withUpperCase().formatHex(hashRepetit);

        // Comprovem si coincideixen o no
        if (textOut.equals(hashRepetitHex)) {
            System.out.println("El hash del mateix missatge es mante");
        } else {
            System.out.println("Els hashes no coincideixen.");
        }

        System.out.print("Entra un segon text: ");
        String segonText = llegirString();

        // convertim el text en un array de bytes
        byte[] segonBytes = segonText.getBytes(StandardCharsets.UTF_8);

        // calculem el hash
        byte[] segonHash = sha256.digest(segonBytes);

        // El convertim en hexadecimal
        String segonHashHex = HexFormat.of().withUpperCase().formatHex(segonHash);

        // Comparem els dos hashes:

        if (textOut.equals(segonHashHex)) {
            System.out.println("Els dos hashes coincideixen: Els textos són iguals");
        } else {
            System.out.println("Els dos hashes no coincideixen: els textos són diferents");
        }
        System.out.println("Prem Enter per tornar al menú...");
        sc.nextLine();
    }

    public static void ex2() throws NoSuchAlgorithmException, IOException {
        // Demanem el missatge
        System.out.println("Escriu un missatge:");
        String missatge = llegirString();

        // Ho guardem al document "missatge.txt"
        Files.writeString(Path.of("missatge.txt"), missatge);

        // Calculem el hash del missatge i el passem a hexadecimal
        byte[] bytesMissatge = missatge.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = sha256.digest(bytesMissatge);
        String hashOriginal = HexFormat.of().withUpperCase().formatHex(hashBytes);

        // Ho guardem al document "missatge.hash"
        Files.writeString(Path.of("missatge.hash"), hashOriginal);

        System.out.print("Quin fitxer vols llegir? ");
        String nomFitxer = llegirString();

        // Llegim el contingut del document seleccionat i el guardem a un String
        String missatgeRebut = Files.readString(Path.of(nomFitxer));

        // Convertim el missatge a bytes
        byte[] bytesRebut = missatgeRebut.getBytes(StandardCharsets.UTF_8);

        // Calculem el hash
        byte[] hashRebutBytes = sha256.digest(bytesRebut);

        // El convertim a hexadecimal
        String hashRebut = HexFormat.of().withUpperCase().formatHex(hashRebutBytes);

        // Llegim el hash original del missatge
        String hashOriginalDesat = Files.readString(Path.of("missatge.hash"));

        // Comparem els dos hashes
        if (hashOriginalDesat.equals(hashRebut)) {
            System.out.println("El missatge rebut coincideix amb l'original.");
        } else {
            System.out.println("S'ha detectat una modificació del missatge.");
        }
    }

    public static String llegirString() {
        String frase = "";

        try {
            frase = sc.nextLine();
        } catch (Exception e) {
            System.out.println("Error, torna-ho a intentar!");
        }

        return frase;
    }

    public static int llegirEnter() {
        int nombre = 0;

        try {
            nombre = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException err) {
            System.out.println("Has d'introduïr un nombre!");
            sc.nextLine();
        }
        return nombre;
    }

    public static boolean sortirMenu() {
        return false;
    }
}
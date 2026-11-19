
package com.example.filemanagement;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class Main {

    // 1. Lecture avec BufferedReader
    public static void readFileWithBufferedReader(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture : " + e.getMessage());
        }
    }

    // 2. Écriture avec BufferedWriter
    public static void writeFileWithBufferedWriter(String filePath, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
            bw.newLine();
            bw.write("Ligne ajoutée avec succès.");

            System.out.println("Fichier écrit avec succès.");

        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture : " + e.getMessage());
        }
    }

    // 3. Écriture avec PrintWriter
    public static void writeWithPrintWriter(String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(filePath))) {

            pw.println("=== Rapport des employés ===");
            pw.printf("Nom: %s, Âge: %d, Poste: %s%n", "Alice", 25, "Engineer");
            pw.printf("Nom: %s, Âge: %d, Poste: %s%n", "Bob", 30, "Manager");
            pw.println("Fin du rapport.");

            if (pw.checkError()) {
                System.err.println("Une erreur est survenue pendant l'écriture.");
            } else {
                System.out.println("Rapport créé avec succès.");
            }

        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    // 4. Lecture avec Files API
    public static void readWithFilesApi(String filePath) {
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture : " + e.getMessage());
        }
    }

    // 5. Écriture avec Files API
    public static void writeWithFilesApi(String filePath, String content) {
        try {
            Path path = Paths.get(filePath);

            Files.write(path, content.getBytes());

            System.out.println("Fichier écrit avec succès : " + filePath);

        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture : " + e.getMessage());
        }
    }

    // 6. Copie d'un fichier
    public static void copyFile(String source, String destination) {
        try {
            Path sourcePath = Paths.get(source);
            Path destPath = Paths.get(destination);

            Files.copy(sourcePath, destPath,
                    StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Fichier copié avec succès.");

        } catch (IOException e) {
            System.err.println("Erreur lors de la copie : " + e.getMessage());
        }
    }

    // 7. Déplacement d'un fichier
    public static void moveFile(String source, String destination) {
        try {
            Path sourcePath = Paths.get(source);
            Path destPath = Paths.get(destination);

            Files.move(sourcePath, destPath,
                    StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Fichier déplacé avec succès.");

        } catch (IOException e) {
            System.err.println("Erreur lors du déplacement : " + e.getMessage());
        }
    }

    // 8. Suppression d'un fichier
    public static void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);

            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("Fichier supprimé : " + filePath);
            } else {
                System.out.println("Le fichier n'existe pas : " + filePath);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    // 9. Lecture d'un fichier binaire
    public static void readBinaryFile(String filePath) {
        try (BufferedInputStream bis =
                new BufferedInputStream(new FileInputStream(filePath))) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalBytes = 0;

            while ((bytesRead = bis.read(buffer)) != -1) {
                totalBytes += bytesRead;
            }

            System.out.println("Fichier binaire lu : " + filePath);
            System.out.println("Taille totale : " + totalBytes + " octets");

        } catch (IOException e) {
            System.err.println("Erreur de lecture binaire : " + e.getMessage());
        }
    }

    // 10. Écriture d'un fichier binaire
    public static void writeBinaryFile(String filePath, byte[] data) {
        try (BufferedOutputStream bos =
                new BufferedOutputStream(new FileOutputStream(filePath))) {

            bos.write(data);
            bos.flush();

            System.out.println("Fichier binaire écrit avec succès.");
            System.out.println("Taille écrite : " + data.length + " octets");

        } catch (IOException e) {
            System.err.println("Erreur d'écriture binaire : " + e.getMessage());
        }
    }

    // 11. Récupération des métadonnées
    public static void getFileMetadata(String filePath) {
        try {
            Path path = Paths.get(filePath);

            BasicFileAttributes attrs =
                    Files.readAttributes(path, BasicFileAttributes.class);

            System.out.println("=== Métadonnées du fichier ===");
            System.out.println("Nom : " + path.getFileName());
            System.out.println("Taille : " + attrs.size() + " octets");
            System.out.println("Dernière modification : "
                    + attrs.lastModifiedTime().toInstant());
            System.out.println("Est un répertoire : " + attrs.isDirectory());
            System.out.println("Est un fichier : " + attrs.isRegularFile());

        } catch (IOException e) {
            System.err.println("Erreur métadonnées : " + e.getMessage());
        }
    }

    // 12. Programme principal
    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println(" TP 12 : Gestion des fichiers Java");
        System.out.println("==================================");

        // Création du dossier data
        try {
            Files.createDirectories(Paths.get("data"));
        } catch (IOException e) {
            System.err.println("Erreur création dossier : " + e.getMessage());
            return;
        }

        // Création du fichier employees.txt
        String employees = "data/employees.txt";

        try {
            if (!Files.exists(Paths.get(employees))) {
                Files.write(Paths.get(employees), (
                    "Alice,25,Engineer\n" +
                    "Bob,30,Manager\n" +
                    "Charlie,28,Designer\n"
                ).getBytes());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        // 1. Lecture BufferedReader
        System.out.println("\n1. Lecture avec BufferedReader");
        readFileWithBufferedReader(employees);

        // 2. Écriture BufferedWriter
        System.out.println("\n2. Écriture avec BufferedWriter");
        writeFileWithBufferedWriter(
                "data/output.txt",
                "Bienvenue dans le TP Java"
        );

        // 3. PrintWriter
        System.out.println("\n3. Écriture avec PrintWriter");
        writeWithPrintWriter("data/report.txt");

        // 4. Lecture Files API
        System.out.println("\n4. Lecture avec Files API");
        readWithFilesApi(employees);

        // 5. Écriture Files API
        System.out.println("\n5. Écriture avec Files API");
        writeWithFilesApi(
                "data/nio_output.txt",
                "Premier fichier avec java.nio.file.Files"
        );

        // 6. Copie
        System.out.println("\n6. Copie du fichier");
        copyFile(employees, "data/employees_backup.txt");

        // 7. Déplacement
        System.out.println("\n7. Déplacement du fichier");
        moveFile("data/nio_output.txt", "data/moved_output.txt");

        // 8. Lecture binaire
        System.out.println("\n8. Lecture binaire");
        readBinaryFile("data/test.jpg");

        // 9. Écriture binaire
        System.out.println("\n9. Écriture binaire");

        try {
            byte[] imageData = Files.readAllBytes(Paths.get("data/test.jpg"));
            writeBinaryFile("data/copy_binary.bin", imageData);
        } catch (IOException e) {
            System.err.println("Image introuvable : " + e.getMessage());
        }

        // 10. Métadonnées
        System.out.println("\n10. Métadonnées");
        getFileMetadata(employees);

        // 11. Suppression
        System.out.println("\n11. Suppression");
        deleteFile("data/moved_output.txt");

        System.out.println("\n==================================");
        System.out.println("Fin du TP 12 !");
        System.out.println("==================================");
    }
}

package easy.stars.server.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zip {
    private static final int  BUFFER_SIZE = 4096;

    private Zip() {
    }

    private static void extractFile(ZipInputStream in, File outdir, String name) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        File file = new File(outdir,name);
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            int count = -1;
            while ((count = in.read(buffer)) != -1)
                out.write(buffer, 0, count);
            if (!name.contains(".")) file.setExecutable(true);
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    private static boolean mkdirs(File outDir, String path) {
        File d = new File(outDir, path);
        return d.mkdirs();
    }

    private static String dirPart(String name)
    {
        int s = name.lastIndexOf( File.separatorChar );
        return s == -1 ? null : name.substring( 0, s );
    }

    public static void extract(File zipfile, File outDir)
    {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipfile)))
        {
            ZipEntry entry;
            String name;
            String dir;
            while ((entry = zin.getNextEntry()) != null)
            {
                name = entry.getName();
                if( entry.isDirectory() )
                {
                    mkdirs(outDir,name);
                    continue;
                }
                dir = dirPart(name);
                if( dir != null )
                    mkdirs(outDir,dir);

                extractFile(zin, outDir, name);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

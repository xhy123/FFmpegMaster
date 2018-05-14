package com.hhhh.ffmpegmaster.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * Created by xhy on 2018/5/11 0011.
 *
 * @author xhy
 * @data 2018/5/11 0011
 */

public class SDKFileUtils {
    public static final String TAG="SDKFileUtils";
    public static final boolean VERBOSE = false;
    /**
     * 在指定的文件夹里创建一个文件名字, 名字是当前时间,指定后缀.
     * @return
     */
    public static String createFile(String dir,String suffix){
        Calendar c = Calendar.getInstance();
        int  hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DAY_OF_MONTH);
        int second=c.get(Calendar.SECOND);
        int millisecond=c.get(Calendar.MILLISECOND);
        year=year-2000;
        String name=dir;
        File d = new File(name);

        // 如果目录不中存在，创建这个目录
        if (!d.exists())
            d.mkdir();
        name+="/";


        name+=String.valueOf(year);
        name+=String.valueOf(month);
        name+=String.valueOf(day);
        name+=String.valueOf(hour);
        name+=String.valueOf(minute);
        name+=String.valueOf(second);
        name+=String.valueOf(millisecond);
        if(suffix.startsWith(".")==false){
            name+=".";
        }
        name+=suffix;

        try {
            Thread.sleep(1);  //保持文件名的唯一性.
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        File file=new File(name);
        if(file.exists()==false)
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return name;
    }
    /**
     * 在box目录下生成一个mp4的文件,并返回名字的路径.
     * @return
     */
    public static String createMp4FileInBox()
    {
        return createFile(SDKDir.TMP_DIR, ".mp4");
    }
    /**
     * 在box目录下生成一个aac的文件,并返回名字的路径.
     * @return
     */
    public static String createAACFileInBox()
    {
        return createFile(SDKDir.TMP_DIR, ".aac");
    }
    /**
     * 在box目录下生成一个指定后缀名的文件,并返回名字的路径.这里仅仅创建一个名字.
     * @param suffix  指定的后缀名.
     * @return
     */
    public static String createFileInBox(String suffix)
    {
        return createFile(SDKDir.TMP_DIR, suffix);
    }
    /**
     * 只是在box目录生成一个路径字符串,但这个文件并不存在.
     * @return
     */
    public static String newMp4PathInBox()
    {
        return newFilePath(SDKDir.TMP_DIR,".mp4");
    }

    /**
     * 在指定的文件夹里 定义一个文件名字, 名字是当前时间,指定后缀.
     * 注意: 和 {@link #createFile(String, String)}的区别是,这里不生成文件,只是生成这个路径的字符串.
     * @param suffix  ".mp4"
     * @return
     */
    public static String newFilePath(String dir,String suffix){
        Calendar c = Calendar.getInstance();
        int  hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DAY_OF_MONTH);
        int second=c.get(Calendar.SECOND);
        int millisecond=c.get(Calendar.MILLISECOND);
        year=year-2000;
        String name=dir;
        File d = new File(name);

        // 如果目录不中存在，创建这个目录
        if (!d.exists())
            d.mkdir();
        name+="/";

        name+=String.valueOf(year);
        name+=String.valueOf(month);
        name+=String.valueOf(day);
        name+=String.valueOf(hour);
        name+=String.valueOf(minute);
        name+=String.valueOf(second);
        name+=String.valueOf(millisecond);
        name+=suffix;

        try {
            Thread.sleep(1);  //保持文件名的唯一性.
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//				File file=new File(name);
//				if(file.exists()==false)
//				{
//					try {
//						file.createNewFile();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
        return name;
    }
    /**
     * 使用终端的cp命令拷贝文件,拷贝成功,返回目标字符串,失败返回null;
     *
     * 拷贝大文件,则有耗时,可以放到new Thread中进行.
     * @param srcPath
     * @param suffix  后缀名,如".pcm"
     * @return
     */
    public static String copyFile(String srcPath,String suffix)
    {
        String dstPath=SDKFileUtils.createFile(SDKDir.TMP_DIR, suffix);

//			 	String cmd="/system/bin/cp ";
//			 	cmd+=srcPath;
//			 	cmd+=" ";
//			 	cmd+=dstPath;
//				Runtime.getRuntime().exec(cmd).waitFor();

        File srcF=new File(srcPath);
        File dstF=new File(dstPath);

        copyFile(srcF,dstF);

        if(srcF.length()==dstF.length())
            return dstPath;
        else{
            Log.e(TAG,"fileCopy is failed! "+srcPath+" src size:"+srcF.length()+" dst size:"+dstF.length());
            SDKFileUtils.deleteFile(dstPath);
            return null;
        }
    }
    /**
     * 删除指定的文件.
     * @param path
     */
    public static void deleteFile(String path)
    {
        if(path!=null)
        {
            File file=new File(path);
            if(file.exists())
            {
                file.delete();
            }
        }
    }
    /**
     * 判断 两个文件大小相等.
     * @param path1
     * @param path2
     * @return
     */
    public static boolean equalSize(String path1,String path2)
    {
        File srcF=new File(path1);
        File srcF2=new File(path2);
        if(srcF.length()== srcF2.length())
            return true;
        else
            return false;
    }
    public static String getFileNameFromPath(String path){
        if (path == null)
            return "";
        int index = path.lastIndexOf('/');
        if (index> -1)
            return path.substring(index+1);
        else
            return path;
    }

    public static String getParent(String path){
        if (TextUtils.equals("/", path))
            return path;
        String parentPath = path;
        if (parentPath.endsWith("/"))
            parentPath = parentPath.substring(0, parentPath.length()-1);
        int index = parentPath.lastIndexOf('/');
        if (index > 0){
            parentPath = parentPath.substring(0, index);
        } else if (index == 0)
            parentPath = "/";
        return parentPath;
    }
    public static boolean fileExist(String absolutePath)
    {
        if(absolutePath==null)
            return false;
        else{
            File file=new File(absolutePath);
            if(file.exists())
                return true;
        }
        return false;
    }
    public static boolean filesExist(String[] fileArray)
    {

        for(String file: fileArray)
        {
            if(fileExist(file)==false)
                return false;
        }
        return true;
    }
    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public static boolean copyFile(File src, File dst){
        boolean ret = true;
        if (src.isDirectory()) {
            File[] filesList = src.listFiles();
            dst.mkdirs();
            for (File file : filesList)
                ret &= copyFile(file, new File(dst, file.getName()));
        } else if (src.isFile()) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(src));
                out = new BufferedOutputStream(new FileOutputStream(dst));

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                return true;
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } finally {
                close(in);
                close(out);
            }
            return false;
        }
        return ret;
    }
    public static boolean close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
                return true;
            } catch (IOException e) {}
        return false;
    }


    //---------------------------------
    /** 删除空目录
     * @param dir 将要删除的目录路径
     */
    public static void deleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    /**
     *测试

     public static void main(String[] args) {
     doDeleteEmptyDir("new_dir1");
     String newDir2 = "new_dir2";
     boolean success = deleteDir(new File(newDir2));
     if (success) {
     System.out.println("Successfully deleted populated directory: " + newDir2);
     } else {
     System.out.println("Failed to delete populated directory: " + newDir2);
     }
     } */
}

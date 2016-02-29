package com.linkcreators.listviewjson.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * 文件操作工具包
 * Created by Jun on 2015/10/30.
 */
public class FileUtils {

    /**
     * 写文本文件
     * 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
     * @param context
     * @param fileName
     * @param content
     */
    public static void write(Context context, String fileName, String content) throws IOException {
        if(null == content){
            content = "";
        }
        try{
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文本文件
     * @param context
     * @param fileName
     * @return
     */
    public static String read(Context context, String fileName){
        try{
            FileInputStream fileInputStream = context.openFileInput(fileName);
            return readInStream(fileInputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    private static String readInStream(FileInputStream fileInputStream) {
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            int length;
            while(-1 != (length = fileInputStream.read(buffer))){
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            fileInputStream.close();
            return  outputStream.toString();
        }catch (Exception e){
            Log.i("FileTest", e.getMessage());
        }
        return  null;
    }

    /**
     * 向手机写图片
     * @param buffer
     * @param folder
     * @param fileName
     * @return
     */
    public boolean writeFile(byte[] buffer, String folder, String fileName){
        boolean writeSucc = false;
        //判断sdCard是否正常挂载
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        String folderPath = "";

        if(sdCardExist){
            folderPath = Environment.getExternalStorageDirectory() + File.separator
                    +  folder + File.separator;
        }else{
            writeSucc = false;
        }

        File fileDir = new File(folderPath);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }

        File file = new File(folderPath + fileName);
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            outputStream.write(buffer);
            writeSucc = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return writeSucc;
    }

    /**
     * 创建文件
     * @param folderPath
     * @param fileName
     * @return
     */
    public static File createFile(String folderPath, String fileName){

        //创建File对象，参数为String类型，表示目录名
        File destDir = new File(folderPath);

        //判断文件是否存在，如果不存在则创建新目录
        if(!destDir.exists()){
            destDir.mkdirs();
        }
        return new File(folderPath, folderPath + fileName);
    }

    /**
     * 根据文件绝对路径获取文件名
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath){
        if(StringUtils.isEmpty(filePath)){
            return "";
        }
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    /**
     * 根据文件的绝对路径获取文件名但不包含扩展名
     * @param filePath
     * @return
     */
    public static String getFileNameNoFormat(String filePath){
        if(StringUtils.isEmpty(filePath)){
            return "";
        }
        int point = filePath.lastIndexOf('.');
        //截取字符串substring(int beginIndex, int endIndex)
        return  filePath.substring(filePath.lastIndexOf(File.separator) + 1,point);
    }

    /**
     * 获取文件扩展名
     * @param fileName
     * @return
     */
    public static String getFileFormat(String fileName){
        if(StringUtils.isEmpty(fileName)){
            return "";
        }
        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }

    /**
     * 获取文件大小
     * @param filePath
     * @return
     */
    public static long getFileSize(String filePath){
        long size = 0;

        File file = new File(filePath);
        if(file.exists() && null != file){
            size = file.length();
        }
        return size;
    }

    /**
     * 获取文件大小
     * @param size 字节
     * @return
     */
    public static String getFileSize(long size){
        if(size <= 0){
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        float temp = (float)size / 1024;
        if(temp >= 1024){
            return decimalFormat.format((temp / 1024) + "M");
        }else {
            return decimalFormat.format(temp) + "K";
        }
    }

    /**
     * 转换文件大小
     * @param fileSize
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileSize){
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String fileSizeString = "";
        if(fileSize < 1024){
            fileSizeString = decimalFormat.format((double)fileSize) + "B";
        }else if(fileSize < 1048576){
            fileSizeString = decimalFormat.format((double)fileSize / 1024) + "KB";
        }else if(fileSize < 1073741824){
            fileSizeString = decimalFormat.format((double)fileSize / 1048576) + "M";
        }else{
            fileSizeString = decimalFormat.format((double)fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取目录下文件大小
     * @param fileDir
     * @return
     */
    public static long getDirSize(File fileDir){
        if(null == fileDir){
            return 0;
        }
        if(!fileDir.isDirectory()){
            return 0;
        }
        long dirSize = 0;
        File[] files = fileDir.listFiles();
        for(File file : files){
            if(file.isFile()){
                dirSize += file.length();
            }else if(file.isDirectory()){
                dirSize += file.length();
                //递归调用继续统计
                dirSize += getDirSize(file);
            }
        }
        return dirSize;
    }

    /**
     * 获取目录下文件个数
     * @param dirFile
     * @return
     */
    public long getFileList(File dirFile){
        long count = 0;
        File[] files = dirFile.listFiles();
        count = files.length;
        for(File file : files){
            if(file.isDirectory()){
                //递归获取
                count = count + getFileList(file);
                count--;
            }
        }
        return count;
    }

    /**
     * 检查文件是否存在
     * @param name
     * @return
     */
    public static boolean checkFileExists(String name){
        boolean status;
        if(!name.equals("")){
            File path = new File( Environment.getExternalStorageDirectory().toString() + name);
            status = path.exists();
            return status;
        }else{
            return false;
        }
    }

    /**
     * 计算SD卡的剩余空间
     * @return 返回-1，说明没有安装sd卡
     */
    public static long getFreeDiskSpace(){
        String status = Environment.getExternalStorageState();
        long freeSpace = 0;
        if(status.equals(Environment.MEDIA_MOUNTED)){
            try{
                File path = Environment.getExternalStorageDirectory();
                StatFs statFs = new StatFs(path.getPath());
                //获取每个block的SIZE
                long blockSize = statFs.getBlockSizeLong();
                //空闲的Block的数量
                long availableBlocks = statFs.getAvailableBlocksLong();
                freeSpace = availableBlocks * blockSize / 1024;
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            return  -1;
        }
        return (freeSpace);
    }

    /**
     * 新建目录
     * @param directoryName
     * @return
     */
    public static boolean createDirectory(String directoryName){
        boolean status;
        if(!directoryName.equals("")) {
            File path = new File(Environment.getExternalStorageDirectory().toString() + directoryName);
            status = path.mkdirs();
            return  status;
        }else{
            return  false;
        }
    }

    /**
     * 检查是否安装SD卡
     * @return
     */
    public static boolean checkSaveLocationExists(){
        String sdCardStatus = Environment.getExternalStorageState();
        if(sdCardStatus.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除目录(包括：目录里的所有文件)
     * @param fileDir
     * @return
     */
    public static boolean deleteDirectory(String fileDir){
        SecurityManager securityManager = new SecurityManager();
        if(!fileDir.equals("")){
            File path = new File(Environment.getExternalStorageDirectory().toString() + fileDir);
            securityManager.checkDelete(path.toString());
            if(path.isDirectory()){
                String[] listFile = path.list();
                // delete all files within the specified directory and then
                // delete the directory
                try{
                    for(int i = 0; i < listFile.length; i++){
                        File deletedFile = new File(path.toString() + "/" + listFile[i].toString());
                        deletedFile.delete();
                    }
                    path.delete();
                    Log.i("DirectoryManager deleteDirectory", fileDir);
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * 删除文件
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String fileName){
        SecurityManager securityManager = new SecurityManager();
        if(!fileName.equals("")){
            File path = new File(Environment.getExternalStorageDirectory().toString() + fileName);
            securityManager.checkDelete(path.toString());
            if(path.isFile()){
                try{
                    Log.i("DirectoryManager deleteFile", fileName);
                    path.delete();
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * inputStream转换成byte[]
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] toBytes(InputStream inputStream) throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int temp;
        while(-1 != (temp = inputStream.read())){
            outputStream.write(temp);
        }
       // 创建一个大小与此输出流的当前大小的一个新分配缓冲区
        byte buffer[] = outputStream.toByteArray();
        outputStream.close();
        return buffer;
    }

}


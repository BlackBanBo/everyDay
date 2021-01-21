package day.crease.day.util;


import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Vector;

/**
 * 文件的基本操作：上传、下载
 * 操作文件服务器上的文件：增删改查
 * 文件传输的两种途径：ftp/sftp(传输加密，安全性高但性能低)，根据应用场景不同选择合适的传输协议
 * 这里用sftp传输
 */
public class FileUtil {
    private Logger logger = LogManager.getLogger(FileUtil.class);
    private ChannelSftp sftp;
    private Session session;
    //服务器IP地址
    private String IP;
    //服务器端口号
    private int port;
    //用户名
    private String username;
    //密码
    private String password;
    //连接密钥
    private String privateKey;

    /**
     * 创建构造器：基于密码认证sftp对象
     * @param username 登录用户名
     * @param password 登录密码
     * @param port 端口
     * @param IP 文件服务器IP地址
     */
    public FileUtil(String username, String password, int port, String IP){
        this.username = username;
        this.password = password;
        this.port = port;
        this.IP = IP;
    }

    /**
     * 基于私钥认证sftp对象
     * @param username 登录用户名
     * @param privateKey 密钥
     * @param port 端口
     * @param IP 文件服务器IP地址
     */
    public FileUtil(String username, String privateKey, String IP, int port){
        this.username = username;
        this.privateKey = privateKey;
        this.IP = IP;
        this.port = port;
    }

    /**
     * 连接SFTP服务器
     */
    public void connect(){
        JSch jSch = new JSch();
        try{
            if(privateKey != null){
                //设置服务器的登录密钥
                jSch.addIdentity(privateKey);
            }
            //采用指定的端口连接服务器
            session = jSch.getSession(username,IP,port);
            if(password != null){
                session.setPassword(password);
            }
            //优先使用password验证，session.conn()性能低，使用password验证可以跳过gssapi认证，提升连接速度
            session.setConfig("PreferredAuthentications","password");
            //设置第一次登录的时候提示：可选值：(ask | yes | no)
            session.setConfig("StrictHostKeyChecking","no");
            session.connect();
            //创建sftp通信通道
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            logger.info("sftp server connect sucess!");
        }catch (Exception e){
            logger.info("SFTP server error!",e);
        }
    }

    /**
     * 关闭SFTP连接
     */
    public void disconnect(){
        if(sftp != null){
            if(sftp.isConnected()){
                sftp.disconnect();
                logger.info("SFTP is closed!");
            }
        }
        if(session != null){
            if(session.isConnected()){
                session.disconnect();
                logger.info("session is closed!");
            }
        }
    }

    /**
     * 上传文件到sftp服务器上
     * @param directoy 上传到sftp服务器上的路径
     * @param sftpFileName 上传之后的文件名
     * @param inputStream 输入流
     */
    public void uploadFile(String directoy, String sftpFileName, InputStream inputStream) throws SftpException {
        //获取系统时间，单位为毫秒数
        long start = System.currentTimeMillis();
        try{
            //如果文件夹不存在，则创建文件夹
            if(sftp.ls(directoy) == null){
                sftp.mkdir(directoy);
            }
            //切换到指定文件夹
            sftp.cd(directoy);
        }catch (Exception e){
            //创建文件夹并切换到该文件夹
            sftp.mkdir(directoy);
            sftp.cd(directoy);
        }
        sftp.put(inputStream,sftpFileName);
        logger.info("文件上传成功，耗时：{}ms",(System.currentTimeMillis() - start));
    }

    /**
     * 上传单个文件
     * @param directory 上传到sftp服务器的路径
     * @param uploadFileUrl 文件所在本地路径
     */
    public void uploadFile(String directory,String uploadFileUrl) throws SftpException{
        File file = new File(uploadFileUrl);
        try{
            uploadFile(directory,file.getName(),new FileInputStream(file));
        }catch (FileNotFoundException e){
            logger.error("上传文件异常！", e);
        }
    }

    /**
     * 将byte[] 字节数组作为文件上传到sftp文件服务器
     * @param directory  传到sftp服务器的路径
     * @param sftpFileName 上传之后的文件名
     * @param bytes 字节数组
     */
    public void uploadFile(String directory,String sftpFileName,byte[] bytes){
        try{
            uploadFile(directory,sftpFileName,new ByteArrayInputStream(bytes));
        }catch (SftpException e){
            logger.info("文件上传失败",e);
        }
    }

    /**
     * 将字符串按照指定编码格式上传到sftp服务器上
     * @param directory  传到sftp服务器的路径
     * @param sftpFileName 上传之后的文件名
     * @param str 字符串
     * @param charset 字符编码
     */
    public void uploadFile(String directory,String sftpFileName,String str,String charset) throws SftpException{
        try{
            uploadFile(directory,sftpFileName,new ByteArrayInputStream(str.getBytes(charset)));
        }catch (UnsupportedEncodingException e){
            logger.info("上传文件异常",e);
        }
    }

    /**
     * 下载ftp服务器上的文件
     * @param directory ftp服务器上的文件路径
     * @param sftpFileName ftp服务器上的文件名
     * @param savePath 本地保存路径
     */
    public void download(String directory,String sftpFileName,String savePath){
        try{
            if(directory != null && !"".equals(directory)){
                sftp.cd(directory);
            }
            File file = new File(savePath);
            sftp.get(sftpFileName,new FileOutputStream(file));
        }catch (FileNotFoundException | SftpException e){
            logger.error("文件下载异常！", e);
        }
    }

    /**
     *下载文件为字节数组
     * @param directory SFTP服务器的文件路径
     * @param downloadFile SFTP服务器上的文件名
     * @return 字节数组
     */
    public byte[] download(String directory, String downloadFile) {
        try{
            if(directory != null && !"".equals(directory)){
                sftp.cd(directory);
            }
            InputStream inputStream = sftp.get(downloadFile);
            return IOUtils.toByteArray(inputStream);
        }catch (SftpException | IOException e){
            logger.error("文件下载异常！", e);
        }
        return null;
    }

    /**
     * 下载文件
     * @param directory  SFTP服务器的文件路径
     * @param downloadFile SFTP服务器上的文件名
     * @return 输入流
     */
    public InputStream downloadStream(String directory, String downloadFile) {
           try{
               if(directory != null && !"".equals(directory)){
                   sftp.cd(directory);
               }
               return sftp.get(downloadFile);
           }catch (SftpException e){
               logger.error("文件下载异常！", e);
           }
           return null;
    }

    /**
     * 获取文件夹下的文件
     * @param directory SFTP服务器的文件路径
     * @return
     */
    public Vector<?> listFiles(String directory){
        try{
            if(isDirExist(directory)){
                Vector<?> vector =  sftp.ls(directory);
                //移除上级目录和根目录
                vector.remove(0);
                vector.remove(0);
                return vector;
            }
        }catch (SftpException e){
            logger.error("获取文件夹信息异常！", e);
        }
        return  null;
    }

    /**
     * 检测文件夹是否存在
     * @param directory sftp服务器上文件夹所在路径
     */
    public boolean booleanUrl(String directory){
        try{
            if(sftp.ls(directory) == null){
                logger.info("该文件夹{}不存在",directory);
                return false;
            }
        }catch (SftpException e){
            logger.info("检测异常",e);
        }
        return true;
    }

    /**
     * 判断目录是否存在
     * @param directory SFTP服务器的文件路径
     * @return
     */
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try{
            SftpATTRS sftpATTRS = this.sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        }catch (SftpException e){
            if(e.getMessage().toLowerCase().equals("no such file")){
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    /**
     * 删除指定文件
     * @param directory SFTP服务器的文件路径
     * @param deleteFileName SFTP服务器文件名
     */
    public void delete(String directory,String deleteFileName){
        try{
            sftp.ls(directory);
            sftp.rm(deleteFileName);
        }catch (SftpException e){
            logger.info("文件删除异常",e);
        }
    }

    /**
     * 删除文件夹
     * @param directory
     */
    public void delete(String directory){
        Vector vector = listFiles(directory);
        vector.remove(0);
        vector.remove(0);
        for(Object v:vector){
            ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry)v;
            try{
                sftp.cd(directory);
                sftp.rm(lsEntry.getFilename());
            }catch (SftpException e){
                logger.info("删除异常",e);
            }
        }
    }

    /**
     * 创建文件目录
     * @param createPath
     * @return
     */
    public boolean createDir(String createPath){
        //首先判断这个目录是否已经存在
        try{
            if(isDirExist(createPath)){
                this.sftp.cd(createPath);
                return true;
            }
            String pathArry[] = createPath.split("/");
            StringBuffer filePath  = new StringBuffer("/");
            for(String path:pathArry){
                if(path.equals("")){
                    continue;
                }
                filePath.append(path + "/");
                if(isDirExist(filePath.toString())){
                    sftp.cd(filePath.toString());
                }else {
                    //新建目录
                    sftp.mkdir(filePath.toString());
                    //进入目录
                    sftp.cd(filePath.toString());
                }
            }
            this.sftp.cd(createPath);
        }catch (SftpException e){
            logger.info("创建目录异常",e);
            return false;
        }
        return true;
    }
}

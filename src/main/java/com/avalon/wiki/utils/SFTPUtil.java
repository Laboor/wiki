package com.avalon.wiki.utils;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

public class SFTPUtil {
    private final static Logger logger = LoggerFactory.getLogger(SFTPUtil.class);

    private String host;       // 主机IP

    private int port;          // 端口

    private String username;   // 用户名

    private String password;   // 密码

    private String privateKey; // 密钥文件路径

    private String passphrase; // 密钥口令

    private int connectTimeout = 10000;  // 连接超时时间

    private static ChannelSftp sftp;

    private static Session sshSession;


    public SFTPUtil(String username, String password, String host, int port) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public SFTPUtil(String username, String password, String host, int port, String privateKey, String passphrase) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.privateKey = privateKey;
        this.passphrase = passphrase;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * 连接SFTP
     */
    public void connect() {
        JSch jsch = new JSch();
        try {
            if (!ObjectUtils.isEmpty(privateKey)) {
                // 使用密钥验证方式，密钥可以使有口令的密钥，也可以是没有口令的密钥
                if (!ObjectUtils.isEmpty(passphrase)) {
                    jsch.addIdentity(privateKey, passphrase);
                } else {
                    jsch.addIdentity(privateKey);
                }
            }
            sshSession = jsch.getSession(username, host, port);
            if (!ObjectUtils.isEmpty(password)) {
                sshSession.setPassword(password);
            }
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");// do not verify host
            // key
            sshSession.setConfig(sshConfig);
            sshSession.connect(connectTimeout);
            // 参数sftp指明要打开的连接是sftp连接
            Channel channel = sshSession.openChannel("sftp");
            channel.connect(connectTimeout);
            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            logger.error("SFTP连接【" + host + ":" + port + "】异常", e);
        }
    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (sshSession != null) {
            if (sshSession.isConnected()) {
                sshSession.disconnect();
            }
        }
    }

    /**
     * 是否连接
     */
    public boolean isConnected() {
        return sftp != null && sftp.isConnected();
    }

    /**
     * 下载文件
     */
    public InputStream downFile(String remotePath, String remoteFile) throws SftpException {
        try {
            if (sftp == null || !isConnected()) {
                connect();
            }
            sftp.cd(remotePath);
            return sftp.get(remoteFile);
        } catch (SftpException e) {
            logger.error("SFTP文件下载失败或文件不存在！", e);
            throw e;
        }
    }

    public byte[] downLoad(String remotePath, String remoteFile) throws SftpException, IOException {
        InputStream input = null;
        try {
            if (sftp == null || !isConnected()) {
                connect();
            }
            sftp.cd(remotePath);
            input = sftp.get(remoteFile);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[10485760];
            int n;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        } catch (SftpException e) {
            logger.error("SFTP文件下载失败或文件不存在！", e);
            throw e;
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * 下载单个文件
     *
     * @param remoteFileName 下载文件名
     * @param localPath      本地保存目录(以路径符号结束)
     * @param localFileName  保存文件名
     * @return
     */
    public synchronized boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName) throws FileNotFoundException, SftpException {
        logger.info(remotePath + "/" + remoteFileName + "/" + localPath + "/" + localFileName);
        try {
            if (sftp == null || !isConnected()) {
                connect();
            }
            sftp.cd(remotePath);
            File file = new File(localPath + localFileName);
            mkdirs(localPath + localFileName);
            sftp.get(remoteFileName, new FileOutputStream(file));
            return true;
        } catch (FileNotFoundException e) {
            logger.error("SFTP不存在文件,Path:" + remotePath + ",file:" + remoteFileName, e);
            throw e;
        } catch (SftpException e) {
            logger.error("SFTP下载文件处理异常,Path:" + remotePath + ",file:" + remoteFileName, e);
            throw e;
        }
    }

    /**
     * 上传(input上传完成,并未关闭,在外层调用处虚处理)
     */
    public void uploadFile(String remotePath, String fileName, InputStream input) throws SftpException, IOException {
        try {
            if (sftp == null || !isConnected()) {
                connect();
            }
            if (!openDir(remotePath)) {
                createDir(remotePath);
            }
            sftp.put(input, fileName);
        } catch (SftpException e) {
            logger.error("SFTP文件上传异常！", e);
            throw e;
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * 上传 不关闭任何流
     */
    public void uploadFileNotClose(String remotePath, String fileName, InputStream input) throws SftpException {
        try {
            if (sftp == null || !isConnected()) {
                connect();
            }
            if (!openDir(remotePath)) {
                createDir(remotePath);
            }
            sftp.put(input, fileName);
        } catch (SftpException e) {
            logger.error("SFTP文件上传异常！", e);
            throw e;
        }
    }

    /**
     * 上传单个文件
     *
     * @param remotePath     远程保存目录
     * @param remoteFileName 保存文件名
     * @param localPath      本地上传目录(以路径符号结束)
     * @param localFileName  上传的文件名
     * @return
     */
    public boolean uploadFile(String remotePath, String remoteFileName, String localPath, String localFileName) throws IOException, SftpException {
        File fileInput = new File(localPath + localFileName);
        return uploadFile(remotePath, remoteFileName, fileInput);
    }

    /**
     * 上传单个文件
     *
     * @param remotePath     远程保存目录
     * @param remoteFileName 保存文件名
     * @param fileInput      上传的文件
     * @return
     */
    public boolean uploadFile(String remotePath, String remoteFileName, File fileInput) throws IOException, SftpException {
        FileInputStream input = null;
        try {
            input = new FileInputStream(fileInput);
            uploadFile(remotePath, remoteFileName, input);
            return true;
        } catch (SftpException e) {
            logger.error("SFTP上传单个文件异常", e);
            throw e;
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    public boolean deleteFile(String remotePath, String remoteFile) throws SftpException {
        try {
            if (sftp == null || !isConnected()) {
                connect();
            }
            if (openDir(remotePath)) {
                sftp.rm(remoteFile);
            }
            return true;
        } catch (SftpException e) {
            logger.error("SFTP删除文件【" + remotePath + "/" + remoteFile + "】异常", e);
            throw e;
        }
    }

    public boolean renameFile(String remotePath, String oldFileName, String newFileName) throws SftpException {
        try {
            if (sftp == null || !isConnected()) {
                connect();
            }
            if (openDir(remotePath)) {
                sftp.rename(oldFileName, newFileName);
            }
            return true;
        } catch (SftpException e) {
            logger.error("SFTP重命名文件【" + remotePath + "/" + oldFileName + "】异常", e);
            throw e;
        }
    }

    /**
     * 创建目录
     *
     * @param createPath
     * @return
     */
    private boolean createDir(String createPath) {
        try {
            if (isDirExist(createPath)) {
                sftp.cd(createPath);
                return true;
            }
            String[] pathArry = createPath.split("/");
            StringBuilder filePath = new StringBuilder("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                if (!isDirExist(filePath.toString())) {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                }
                sftp.cd(filePath.toString());
            }
            return true;
        } catch (SftpException e) {
            logger.error("SFTP创建目录异常", e);
        }
        return false;
    }

    /**
     * 如果目录不存在就创建目录
     *
     * @param path
     */
    private boolean mkdirs(String path) {
        File f = new File(path);
        String fs = f.getParent();
        f = new File(fs);
        if (!f.exists()) {
            return f.mkdirs();
        }
        return false;
    }

    /**
     * 打开文件夹一层一层
     *
     * @param directory
     * @return
     */
    public boolean openDir(String directory) {
        try {
            sftp.cd(directory);
            return true;
        } catch (SftpException e) {
            logger.error("SFTP打开文件夹【" + directory + "】异常", e);
            return false;
        }
    }

    /**
     * 获取文件列表
     */
    public Vector<ChannelSftp.LsEntry> listFiles(String remotePath) {
        Vector<ChannelSftp.LsEntry> vector = null;
        try {
            if (sftp == null || !isConnected()) {
                connect();
            }
            sftp.cd("/");
            vector = sftp.ls(remotePath);
        } catch (SftpException e) {
            logger.error("SFTP获取文件列表异常", e);
        }
        return vector;
    }

    /**
     * 判断文件是否存在
     *
     * @param directory
     * @return
     */
    public boolean isFileExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            if (sftp == null || !isConnected()) {
                connect();
            }
            sftp.lstat(directory);
            isDirExistFlag = true;
        } catch (SftpException e) {
            logger.error("SFTP读取文件异常", e);
        }
        return isDirExistFlag;
    }

    /**
     * 判断目录是否存在
     *
     * @param directory
     * @return
     */
    public boolean isDirExist(String directory) {
        try {
            if (sftp == null || !isConnected()) {
                connect();
            }
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            return sftpATTRS.isDir();
        } catch (SftpException e) {
            logger.error("SFTP读取目录异常", e);
        }
        return false;
    }
}

public interface IFileInfo {

    String relativePath = "/Books.txt";
    String absolutePath = System.getProperty("user.dir").concat(relativePath);
}

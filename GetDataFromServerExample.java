public class GetDataFromServerExample {
    public static void main(String[] args) throws Exception {
        URLConnection urlConnection = url.openConnection();
        
        urlConnection.connect();
        
        urlConnection.getInputStream();
    }
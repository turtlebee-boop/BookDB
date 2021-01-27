package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbController {

    private static final String CONN = "jdbc:sqlite:books.db";

    public static void addToDB(Book book) {
        String sql = "INSERT INTO books (title, pages ) VALUES (?, ?);";
        try {
            Connection connection = DriverManager.getConnection(CONN);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getPages());
            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> getFromDB(){
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books;";

        try {
            Connection connection = DriverManager.getConnection(CONN);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String title = resultSet.getString("title");
                int pages = resultSet.getInt("pages");
                Book book = new Book(title, pages);
                books.add(book);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
}

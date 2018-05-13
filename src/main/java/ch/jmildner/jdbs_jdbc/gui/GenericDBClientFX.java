package ch.jmildner.jdbs_jdbc.gui;

import ch.jmildner.tools.MyPoolingDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GenericDBClientFX extends Application
{

    private final TextField tableName = new TextField("person2");
    private final TextArea ta = new TextArea();

    private final Button showTables = new Button("SHOW-TABLES");
    private final Button showData = new Button("SHOW-DATA");
    private final Button showMeta = new Button("SHOW-META");
    private final Button clear = new Button("CLEAR");
    private final Button exit = new Button("EXIT");

    private final String DATABASE = "H2";
    private final DataSource DS = new MyPoolingDataSource(DATABASE).getDataSource();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        Pane pane = makeTheLayout();

        addTheListener();
        stage.setOnCloseRequest(e -> exit());

        showTheLayout(stage, pane);
    }

    private void addTheListener()
    {
        showTables.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent e)
            {
                showTables();
            }
        });

        showMeta.setOnAction(e -> showMeta());
        showData.setOnAction(e -> showData());

        clear.setOnAction(e ->
        {
            tableName.clear();
            ta.setText("");
        });

        exit.setOnAction(e -> exit());
    }

    private void exit()
    {
        // hier kommen die aufraeumarbeiten

        System.exit(0);
    }

    private void showTables()
    {
        ta.appendText("showTables\n");
    }

    private void showMeta()
    {
        ta.appendText("showMeta\n");
    }

    private void showData()
    {
        String tname = tableName.getText().trim().toUpperCase();
        ta.appendText("showData - Table: " + tname + "\n");
        try (final Connection c = DS.getConnection())
        {
            try (final Statement s = c.createStatement())
            {
                s.execute("select * from " + tname);

                try (final ResultSet rs = s.getResultSet())
                {
                    ResultSetMetaData md = rs.getMetaData();

                    int anz = md.getColumnCount();

                    for (int i = 1; i <= anz; i++)
                    {
                        ta.appendText(String.format("%-20s",
                                md.getColumnLabel(i)));
                        ta.appendText("\t");
                    }

                    ta.appendText("\n");

                    while (rs.next())
                    {
                        for (int i = 1; i <= anz; i++)
                        {
                            Object o = rs.getObject(i);
                            // System.out.println(o.getClass());
                            ta.appendText(String.format("%-20s", o));
                            ta.appendText("\t");
                        }

                        ta.appendText("\n");
                    }

                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace(System.err);
        }

    }

    private void showTheLayout(Stage stage, Pane pane)
    {
        stage.setScene(new Scene(pane));
        stage.setTitle("Generic DB Client");
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.setX(600);
        stage.setY(30);
        stage.show();
        stage.setHeight(1000);
        stage.setWidth(900);
    }

    private Pane makeTheLayout()
    {
        int zeile = 0;

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(8);
        gridPane.setPadding(new Insets(10)); // aeusserer rand

        zeile++;
        gridPane.add(new Label("TabellenName"), 1, zeile);
        gridPane.add(tableName, 2, zeile, 5, 1);

        zeile++;
        gridPane.add(showTables, 2, zeile);
        gridPane.add(showData, 3, zeile);
        gridPane.add(showMeta, 4, zeile);
        gridPane.add(clear, 5, zeile);
        gridPane.add(exit, 6, zeile);

        zeile++;
        ta.setEditable(false);
        ta.setMinSize(400, 800);
        gridPane.add(ta, 1, zeile, 6, 1);

        gridPane.setGridLinesVisible(false);

        return gridPane;
    }

}

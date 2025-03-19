module co.edu.uniquindio.notas {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    opens co.edu.uniquindio.notas to javafx.fxml;

    exports co.edu.uniquindio.notas;
    exports co.edu.uniquindio.notas.modelo;

    exports co.edu.uniquindio.notas.controladores;
    opens co.edu.uniquindio.notas.controladores to javafx.fxml;
}
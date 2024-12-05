module com.example.gruppcadettsplitterpipergames {
    requires javafx.controls;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;


    opens com.example.gruppcadettsplitterpipergames.entities to org.hibernate.orm.core;
    exports com.example.gruppcadettsplitterpipergames;
}
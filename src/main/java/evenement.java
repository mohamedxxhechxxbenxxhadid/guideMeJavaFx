import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;



public class evenement {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtDate;
    private JTextField txtDescription;
    private JTextField txtLocation;
    private JTextField txtPrix;
    private JButton saveButton;
    private JTable table1;
    private JButton UPDATEButton;
    private JButton DELETEButton;
    private JButton SEARCHButton;
    private JTextField txtId;
    private JScrollPane table_1;

    Connection con;
    PreparedStatement pst;

    public static void main(String[] args) {
        JFrame frame = new JFrame("evenement");
        frame.setContentPane(new evenement().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/pi", "root", "");
            System.out.println("success");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    void table_load() {
        try {
            pst = con.prepareStatement("select * from evenement");
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Nom");
            model.addColumn("Date");
            model.addColumn("Lieu");
            model.addColumn("Description");
            model.addColumn("Prix");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("evName"),
                        rs.getString("evDate"),
                        rs.getString("evLocation"),
                        rs.getString("evDescription"),
                        rs.getInt("evPrix")
                });
            }

            // Accès à la JTable contenue dans le JScrollPane
            JTable jTable = (JTable) ((JViewport) table_1.getComponent(0)).getView();
            // Définition du modèle de table
            jTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public evenement() {
        connect();
        table_load();
        connect(); // Appel de la méthode connect() pour établir la connexion

        if (con == null) {
            System.out.println("La connexion n'a pas été établie avec succès.");
            return;
        } else {
            System.out.println("La connexion a été établie avec succès.");
        }// Appel de la méthode connect() pour établir la connexion

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String evName, evLocation, evDescription, evDate;
                int evPrix;

                evName = txtName.getText();
                evDate = txtDate.getText();
                evLocation = txtLocation.getText();
                evDescription = txtDescription.getText();

                // Vérification si le champ Prix est vide ou non numérique
                try {
                    evPrix = Integer.parseInt(txtPrix.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Veuillez saisir un prix valide", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                    return; // Arrête l'exécution de la méthode si le prix n'est pas valide
                }

                // Vérification du format de la date
                if (!isValidDateFormat(evDate)) {
                    JOptionPane.showMessageDialog(null, "Veuillez saisir la date au format yyyy-MM-dd", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                    return; // Arrête l'exécution de la méthode si le format de date est incorrect
                }

                // Vérification si tous les champs sont remplis
                if (evName.isEmpty() || evDate.isEmpty() || evLocation.isEmpty() || evDescription.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
                    return; // Arrête l'exécution de la méthode si un champ est vide
                }

                try {
                    pst = con.prepareStatement("insert into evenement(evName,evDate,evLocation,evDescription,evPrix) values(?,?,?,?,?)");
                    pst.setString(1, evName);
                    pst.setString(2, evDate);
                    pst.setString(3, evLocation);
                    pst.setString(4, evDescription);
                    pst.setString(5, String.valueOf(evPrix));
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Événement ajouté");

                    txtName.setText("");
                    txtLocation.setText("");
                    txtDescription.setText("");
                    txtPrix.setText("");
                    txtDate.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        SEARCHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtId.getText();
                    pst = con.prepareStatement("select evName,evDate,evLocation,evDescription,evPrix from evenement where id = ?");
                    pst.setString(1,id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true) {
                        String evName = rs.getString(1);
                        String evDate = rs.getString(2);
                        String evLocation = rs.getString(3);
                        String evDescription = rs.getString(4);
                        int evPrix=rs.getInt(5);

                        txtName.setText(evName);
                        txtDate.setText(evDate);
                        txtLocation.setText(evLocation);
                        txtDescription.setText(evDescription);
                        txtPrix.setText(String.valueOf(evPrix));

                        txtName.requestFocus();
                    } else {
                        txtName.setText("");
                        txtDate.setText("");
                        txtLocation.setText("");
                        txtDescription.setText("");
                        txtPrix.setText("");
                        JOptionPane.showMessageDialog(null,"Événement invalide");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        UPDATEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id,evName,evLocation,evDescription,evDate;
                int evPrix;
                evName = txtName.getText();
                evDate = txtDate.getText();
                evLocation = txtLocation.getText();
                evDescription = txtDescription.getText();
                evPrix = Integer.parseInt(txtPrix.getText());
                id=txtId.getText();

                try {
                    pst = con.prepareStatement("update evenement set evName = ?,evDate = ?,evLocation = ?,evDescription = ?,evPrix=? where id = ?");
                    pst.setString(1, evName);
                    pst.setString(2, evDate);
                    pst.setString(3, evLocation);
                    pst.setString(4, evDescription);
                    pst.setString(5, String.valueOf(evPrix));
                    pst.setString(6, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Enregistrement mis à jour");

                    txtName.setText("");
                    txtDate.setText("");
                    txtLocation.setText("");
                    txtDescription.setText("");
                    txtPrix.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id;
                id = txtId.getText();

                try {
                    pst = con.prepareStatement("delete from evenement  where id = ?");
                    pst.setString(1, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Événement supprimé");
                    txtName.setText("");
                    txtDate.setText("");
                    txtLocation.setText("");
                    txtDescription.setText("");
                    txtPrix.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    // Méthode pour vérifier le format de la date
    private boolean isValidDateFormat(String date) {
        try {
            // Vérifie si la date peut être analysée avec le format attendu
            java.sql.Date.valueOf(date);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

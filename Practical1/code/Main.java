import javax.persistence.*;
import java.util.*;
import javax.swing.JOptionPane;
import java.time.Year;
import javax.swing.text.*;


public class Main extends javax.swing.JFrame {
    
    private final EntityManagerFactory entity_manager_factory = Persistence.createEntityManagerFactory("$objectdb/db/p1.odb");
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Main.class.getName());

    public Main() {
        initComponents();
        
        java.awt.Font comicSans = new java.awt.Font("Comic Sans MS", java.awt.Font.PLAIN, 14);
        
        ((AbstractDocument) txtYear.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (isValidInput(fb.getDocument().getLength(), string)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (isValidInput(fb.getDocument().getLength() - length, text)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isValidInput(int currentLength, String text) {
                return text.matches("\\d*") && (currentLength + text.length()) <= 4;
            }
        });
        
        ((AbstractDocument) txtTopSpeed.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                StringBuilder newText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                newText.insert(offset, string);
                if (isValidInput(newText.toString())) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                StringBuilder newText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
                newText.replace(offset, offset + length, text);
                if (isValidInput(newText.toString())) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isValidInput(String text) {
                return text.matches("\\d{0,5}(\\.\\d?)?") && text.length() <= 6;
            }
        });
        
        ((AbstractDocument) txtRegNumber.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string != null) {
                    super.insertString(fb, offset, string.toUpperCase(), attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null) {
                    super.replace(fb, offset, length, text.toUpperCase(), attrs);
                }
            }
        });

         txtTopSpeed.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    evt.consume();
                }
            }
        });
         
         txtYear.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume(); 
                }
            }
        });
         
         txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                btnSearchActionPerformed(null); 
            }
        });
         
         ((AbstractDocument) txtSearchUpdate.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string != null) {
                    super.insertString(fb, offset, string.toUpperCase(), attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text != null) {
                    super.replace(fb, offset, length, text.toUpperCase(), attrs);
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        txtRegNumber = new javax.swing.JTextField();
        lblRegNumber = new javax.swing.JLabel();
        txtModel = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblMake = new javax.swing.JLabel();
        txtMake = new javax.swing.JTextField();
        txtYear = new javax.swing.JTextField();
        lblYear = new javax.swing.JLabel();
        txtTopSpeed = new javax.swing.JTextField();
        lblTopSpeed = new javax.swing.JLabel();
        btnAddCar = new javax.swing.JButton();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        btnSearch = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtBoxSearchResults = new javax.swing.JTextArea();
        txtSearch = new javax.swing.JTextField();
        jInternalFrame3 = new javax.swing.JInternalFrame();
        lblRegNumber1 = new javax.swing.JLabel();
        txtRegNumberUpdate = new javax.swing.JTextField();
        lblMake1 = new javax.swing.JLabel();
        txtMakeUpdate = new javax.swing.JTextField();
        lblYear1 = new javax.swing.JLabel();
        txtYearUpdate = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtModelUpdate = new javax.swing.JTextField();
        lblTopSpeed1 = new javax.swing.JLabel();
        txtTopSpeedUpdate = new javax.swing.JTextField();
        btnUpdateCar = new javax.swing.JButton();
        btnDeleteCar = new javax.swing.JButton();
        txtSearchUpdate = new javax.swing.JTextField();
        btnSearchUpdate = new javax.swing.JButton();
        jInternalFrame4 = new javax.swing.JInternalFrame();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane2.setBackground(new java.awt.Color(255, 204, 255));
        jTabbedPane2.setName("tab_pane"); // NOI18N
        jTabbedPane2.setNextFocusableComponent(jTabbedPane1);

        jInternalFrame1.setBackground(new java.awt.Color(255, 204, 255));
        jInternalFrame1.setForeground(new java.awt.Color(255, 204, 255));
        jInternalFrame1.setVisible(true);

        lblRegNumber.setText("Registration Number");

        jLabel1.setText("Model");

        lblMake.setText("Make");

        lblYear.setText("Year");

        txtTopSpeed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTopSpeedActionPerformed(evt);
            }
        });

        lblTopSpeed.setText("Top Speed");

        btnAddCar.setText("Add Car");
        btnAddCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTopSpeed)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTopSpeed)
                                    .addComponent(jLabel1)
                                    .addComponent(lblRegNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtRegNumber)
                                    .addComponent(txtModel))
                                .addGap(73, 73, 73)
                                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMake)
                                    .addComponent(txtMake, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblYear)))))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(btnAddCar)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRegNumber)
                    .addComponent(lblMake))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRegNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblYear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(lblTopSpeed)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTopSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(btnAddCar)
                .addGap(26, 26, 26))
        );

        jTabbedPane2.addTab("Save Car", jInternalFrame1);

        jInternalFrame2.setVisible(true);

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        txtBoxSearchResults.setEditable(false);
        txtBoxSearchResults.setColumns(20);
        txtBoxSearchResults.setLineWrap(true);
        txtBoxSearchResults.setRows(5);
        txtBoxSearchResults.setWrapStyleWord(true);
        txtBoxSearchResults.setName("txtBoxResults"); // NOI18N
        jScrollPane2.setViewportView(txtBoxSearchResults);

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearch)
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Search Car", jInternalFrame2);

        jInternalFrame3.setBackground(new java.awt.Color(255, 102, 255));
        jInternalFrame3.setVisible(true);

        lblRegNumber1.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        lblRegNumber1.setText("Registration Number");

        txtRegNumberUpdate.setEditable(false);
        txtRegNumberUpdate.setBackground(new java.awt.Color(0, 0, 255));
        txtRegNumberUpdate.setForeground(new java.awt.Color(255, 255, 255));

        lblMake1.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        lblMake1.setText("Make");

        txtMakeUpdate.setBackground(new java.awt.Color(0, 0, 255));
        txtMakeUpdate.setForeground(new java.awt.Color(255, 255, 255));

        lblYear1.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        lblYear1.setText("Year");

        txtYearUpdate.setBackground(new java.awt.Color(0, 0, 255));
        txtYearUpdate.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jLabel2.setText("Model");

        txtModelUpdate.setBackground(new java.awt.Color(0, 0, 255));
        txtModelUpdate.setForeground(new java.awt.Color(255, 255, 255));

        lblTopSpeed1.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        lblTopSpeed1.setText("Top Speed");

        txtTopSpeedUpdate.setBackground(new java.awt.Color(0, 0, 255));
        txtTopSpeedUpdate.setForeground(new java.awt.Color(255, 255, 255));
        txtTopSpeedUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTopSpeedUpdateActionPerformed(evt);
            }
        });

        btnUpdateCar.setBackground(new java.awt.Color(51, 255, 51));
        btnUpdateCar.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        btnUpdateCar.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateCar.setText("Update Car");
        btnUpdateCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateCarActionPerformed(evt);
            }
        });

        btnDeleteCar.setBackground(new java.awt.Color(255, 0, 51));
        btnDeleteCar.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        btnDeleteCar.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteCar.setText("Delete Car");
        btnDeleteCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCarActionPerformed(evt);
            }
        });

        txtSearchUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchUpdateActionPerformed(evt);
            }
        });

        btnSearchUpdate.setText("Find Car");
        btnSearchUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame3Layout = new javax.swing.GroupLayout(jInternalFrame3.getContentPane());
        jInternalFrame3.getContentPane().setLayout(jInternalFrame3Layout);
        jInternalFrame3Layout.setHorizontalGroup(
            jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame3Layout.createSequentialGroup()
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTopSpeed1)
                            .addGroup(jInternalFrame3Layout.createSequentialGroup()
                                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTopSpeedUpdate)
                                    .addComponent(jLabel2)
                                    .addComponent(lblRegNumber1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtRegNumberUpdate)
                                    .addComponent(txtModelUpdate))
                                .addGap(73, 73, 73)
                                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMake1)
                                    .addComponent(txtMakeUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtYearUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblYear1)
                                    .addComponent(btnDeleteCar)))
                            .addGroup(jInternalFrame3Layout.createSequentialGroup()
                                .addComponent(txtSearchUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSearchUpdate))))
                    .addGroup(jInternalFrame3Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(btnUpdateCar)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jInternalFrame3Layout.setVerticalGroup(
            jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchUpdate))
                .addGap(11, 11, 11)
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRegNumber1)
                    .addComponent(lblMake1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRegNumberUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMakeUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblYear1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtModelUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtYearUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(lblTopSpeed1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTopSpeedUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteCar))
                .addGap(18, 18, 18)
                .addComponent(btnUpdateCar)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Update Car", jInternalFrame3);

        jInternalFrame4.setBackground(new java.awt.Color(255, 255, 51));
        jInternalFrame4.setVisible(true);

        jButton1.setBackground(new java.awt.Color(0, 255, 255));
        jButton1.setText("Calculate Average Speed");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame4Layout = new javax.swing.GroupLayout(jInternalFrame4.getContentPane());
        jInternalFrame4.getContentPane().setLayout(jInternalFrame4Layout);
        jInternalFrame4Layout.setHorizontalGroup(
            jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame4Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jButton1)
                .addContainerGap(99, Short.MAX_VALUE))
        );
        jInternalFrame4Layout.setVerticalGroup(
            jInternalFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame4Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Calculate Avg", jInternalFrame4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCarActionPerformed

        EntityManager entity_manager = entity_manager_factory.createEntityManager();
        try {
            String reg = txtRegNumber.getText().trim();
            String make = txtMake.getText().trim();
            String model = txtModel.getText().trim();
            String yearText = txtYear.getText().trim();
            String speedText = txtTopSpeed.getText().trim();
            
            if (reg.isEmpty() || make.isEmpty() || model.isEmpty() || yearText.isEmpty() || speedText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.");
                return;
            }
            
            make = make.substring(0, 1).toUpperCase() + make.substring(1);

            if (!yearText.matches("\\d{4}") || !txtYear.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid 4-digit year.");
                return;
            }
            int year = Integer.parseInt(txtYear.getText());
            int currentYear = java.time.Year.now().getValue();
            
            if (year < 1886 || year > currentYear) {
                JOptionPane.showMessageDialog(this, "Year must be between 1886 and " + currentYear + ".");
                return;
            }
            
            double speed = Double.parseDouble(txtTopSpeed.getText());
            
            if (speed <= 0) {
                throw new NumberFormatException("Speed must be positive.");
            }
            
            Car existingCar = entity_manager.find(Car.class, reg);
            
            if (existingCar != null) {
                JOptionPane.showMessageDialog(this, "A car with registration number " + reg + " already exists.", "Duplicate Entry", JOptionPane.WARNING_MESSAGE);
                entity_manager.close();
                return;
            }
            
            entity_manager.getTransaction().begin();

            Car car = new Car(reg, make, model, year, speed);
            entity_manager.persist(car);

            entity_manager.getTransaction().commit();
            
            Car savedCar = entity_manager.find(Car.class, reg);
            if (savedCar != null) {
                String message = String.format(
                    "Car successfully added:\n\nRegistration: %s\nMake: %s\nModel: %s\nYear: %d\nTop Speed: %.2f km/h",
                    savedCar.getRegNumber(),
                    savedCar.getMake(),
                    savedCar.getModel(),
                    savedCar.getYear(),
                    savedCar.getTopSpeed()
                );
                JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Car saved, but could not retrieve details.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
           
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid top speed.");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save car: " + e.getMessage());
        } finally {
            if (entity_manager.isOpen()) entity_manager.close();
            txtRegNumber.setText("");
            txtMake.setText("");
            txtModel.setText("");
            txtYear.setText("");
            txtTopSpeed.setText("");
        }
    }//GEN-LAST:event_btnAddCarActionPerformed

    private void txtTopSpeedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTopSpeedActionPerformed
       
    }//GEN-LAST:event_txtTopSpeedActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
       
        EntityManager entity_manager = entity_manager_factory.createEntityManager();
        
        String queryText = txtSearch.getText().trim().toUpperCase();

        try {
            TypedQuery<Car> query = entity_manager.createQuery(
                "SELECT c FROM Car c WHERE c.regNumber LIKE :prefix", Car.class);
            query.setParameter("prefix", queryText + "%"); 

            List<Car> results = query.getResultList();

            if (results.isEmpty()) {
                txtBoxSearchResults.setText("No cars found matching \"" + queryText + "\".");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Car c : results) {
                    sb.append(String.format("Reg: %s | Make: %s | Model: %s | Year: %d | Speed: %.2f km/h\n",
                            c.getRegNumber(), c.getMake(), c.getModel(), c.getYear(), c.getTopSpeed()));
                }
                txtBoxSearchResults.setText(sb.toString());
            }
        } catch (Exception e) {
            txtBoxSearchResults.setText("Error: " + e.getMessage());
        } finally {
            entity_manager.close();
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtTopSpeedUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTopSpeedUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTopSpeedUpdateActionPerformed

    private void btnUpdateCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateCarActionPerformed
        // TODO add your handling code here:
        try{
            String reg = txtRegNumberUpdate.getText().trim().toUpperCase(); // Primary key (unchangeable)
            String make = txtMakeUpdate.getText().trim();
            String model = txtModelUpdate.getText().trim();
            String yearText = txtYearUpdate.getText().trim();
            String speedText = txtTopSpeedUpdate.getText().trim();

            if (make.isEmpty() || model.isEmpty() || yearText.isEmpty() || speedText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled in.");
                return;
            }

            make = make.substring(0, 1).toUpperCase() + make.substring(1);
            
            if (!yearText.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid 4-digit year.");
                return;
            }
            
            int year = Integer.parseInt(yearText);
            int currentYear = Year.now().getValue();
            if (year < 1886 || year > currentYear) {
                JOptionPane.showMessageDialog(this, "Year must be between 1886 and " + currentYear + ".");
                return;
            }
            double speed = Double.parseDouble(speedText);
            if (speed <= 0) throw new NumberFormatException();
            
            EntityManager entity_manager = entity_manager_factory.createEntityManager();
            
            Car car = entity_manager.find(Car.class, reg);
            if (car == null) {
                JOptionPane.showMessageDialog(this, "Car with registration " + reg + " not found.");
                return;
            }
            
            entity_manager.getTransaction().begin();
            car.setMake(make);
            car.setModel(model);
            car.setYear(year);
            car.setTopSpeed(speed);
            entity_manager.getTransaction().commit();
            entity_manager.close();
            JOptionPane.showMessageDialog(this, "Car updated successfully.");
        } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Top speed must be a valid positive number.");
        return;
        } finally {
            txtRegNumberUpdate.setText("");
            txtMakeUpdate.setText("");
            txtModelUpdate.setText("");
            txtYearUpdate.setText("");
            txtTopSpeedUpdate.setText("");
        }
    }//GEN-LAST:event_btnUpdateCarActionPerformed

    private void txtSearchUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchUpdateActionPerformed

    private void btnSearchUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchUpdateActionPerformed
       
        String reg = txtSearchUpdate.getText().trim().toUpperCase();

        if (reg.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a registration number.");
            return;
        }

        EntityManager entity_manager = entity_manager_factory.createEntityManager();
        try {
            Car car = entity_manager.find(Car.class, reg);
            
            if (car == null) {
                JOptionPane.showMessageDialog(this, "No car found with registration: " + reg);
            } else {
                
                txtRegNumberUpdate.setText(car.getRegNumber());
                txtMakeUpdate.setText(car.getMake());
                txtModelUpdate.setText(car.getModel());
                txtYearUpdate.setText("" + String.valueOf(car.getYear()));
                txtTopSpeedUpdate.setText(String.valueOf(car.getTopSpeed()));

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching car: " + e.getMessage());
        } finally {
            entity_manager.close();
        }
    }//GEN-LAST:event_btnSearchUpdateActionPerformed

    private void btnDeleteCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCarActionPerformed
        
        String reg = txtRegNumberUpdate.getText().trim().toUpperCase();

        if (reg.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a registration number to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete the car with registration number: " + reg + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        EntityManager entity_manager = entity_manager_factory.createEntityManager();
        try {
            Car car = entity_manager.find(Car.class, reg);
            if (car == null) {
                JOptionPane.showMessageDialog(this, "No car found with registration number: " + reg);
                return;
            }

            entity_manager.getTransaction().begin();
            entity_manager.remove(car);
            entity_manager.getTransaction().commit();
            entity_manager.close();
            JOptionPane.showMessageDialog(this, "Car deleted successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting car: " + e.getMessage());
        } finally {
            txtRegNumberUpdate.setText("");
            txtMakeUpdate.setText("");
            txtModelUpdate.setText("");
            txtYearUpdate.setText("");
            txtTopSpeedUpdate.setText("");
        }
    }//GEN-LAST:event_btnDeleteCarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        EntityManager entity_manager = entity_manager_factory.createEntityManager();

        try {
            TypedQuery<Double> query = entity_manager.createQuery(
                "SELECT AVG(c.topSpeed) FROM Car c", Double.class);

            Double avgSpeed = query.getSingleResult();

            if (avgSpeed == null) {
                JOptionPane.showMessageDialog(this, "No cars found in the database.");
            } else {
                String message = String.format("Average Top Speed: %.2f km/h", avgSpeed);
                JOptionPane.showMessageDialog(this, message, "Average Speed", JOptionPane.INFORMATION_MESSAGE);
            }
            
            entity_manager.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error calculating average speed: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCar;
    private javax.swing.JButton btnDeleteCar;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchUpdate;
    private javax.swing.JButton btnUpdateCar;
    private javax.swing.JButton jButton1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JInternalFrame jInternalFrame3;
    private javax.swing.JInternalFrame jInternalFrame4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblMake;
    private javax.swing.JLabel lblMake1;
    private javax.swing.JLabel lblRegNumber;
    private javax.swing.JLabel lblRegNumber1;
    private javax.swing.JLabel lblTopSpeed;
    private javax.swing.JLabel lblTopSpeed1;
    private javax.swing.JLabel lblYear;
    private javax.swing.JLabel lblYear1;
    private javax.swing.JTextArea txtBoxSearchResults;
    private javax.swing.JTextField txtMake;
    private javax.swing.JTextField txtMakeUpdate;
    private javax.swing.JTextField txtModel;
    private javax.swing.JTextField txtModelUpdate;
    private javax.swing.JTextField txtRegNumber;
    private javax.swing.JTextField txtRegNumberUpdate;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearchUpdate;
    private javax.swing.JTextField txtTopSpeed;
    private javax.swing.JTextField txtTopSpeedUpdate;
    private javax.swing.JTextField txtYear;
    private javax.swing.JTextField txtYearUpdate;
    // End of variables declaration//GEN-END:variables
}

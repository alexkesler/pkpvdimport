package org.kesler.pkpvdimport.gui;

import com.alee.extended.date.WebDateField;
import net.miginfocom.swing.MigLayout;
import org.kesler.pkpvdimport.domain.Applicant;
import org.kesler.pkpvdimport.domain.Cause;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainView extends JFrame {

    private MainViewController controller;
    private CauseTableModel causeTableModel;
    private ApplicantsListModel applicantsListModel;

    private JTextField serverTextField;
    private WebDateField fromDateField;
    private WebDateField toDateField;


    MainView(MainViewController controller) {
        this.controller = controller;
        createGUI();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel,BoxLayout.Y_AXIS));

        // Панель фильтра
        JPanel filterPanel = new JPanel(new MigLayout());
        fromDateField = new WebDateField(new Date());
        toDateField = new WebDateField(new Date());

        filterPanel.add(new JLabel("От"));
        filterPanel.add(fromDateField, "wrap");
        filterPanel.add(new JLabel("До"));
        filterPanel.add(toDateField);

        JPanel receivePanel = new JPanel();
        receivePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Загрузить"));

        JButton receiveButton = new JButton("Получить");
        receiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.readCauses();
            }
        });

        JButton receiveForPaysButton = new JButton("Получить для платежей");
        receiveForPaysButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.readCausesForPays();
            }
        });

        receivePanel.add(receiveButton);
        receivePanel.add(receiveForPaysButton);


        causeTableModel = new CauseTableModel();
        JTable causeTable = new JTable(causeTableModel);
        causeTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        causeTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                applicantsListModel.setApplicants(causeTableModel.getCauseAt(e.getFirstIndex()).getApplicants());
            }
        });
        JScrollPane causeTableScrollPane = new JScrollPane(causeTable);

        JPanel causePanel = new JPanel(new MigLayout());

        applicantsListModel = new ApplicantsListModel();
        JList<String> applicantsList = new JList<String>(applicantsListModel);
        JScrollPane applicantsListScrollPane = new JScrollPane(applicantsList);

        causePanel.add(new JLabel("Заявители"));
        causePanel.add(applicantsListScrollPane, "grow, pushx");

        // Панель отчетов
        JPanel exportPanel = new JPanel();
        exportPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Отчеты"));

        JButton exportPaysButton = new JButton("Выгрузить список пошлин");
        exportPaysButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.exportPays();
            }
        });


        exportPanel.add(exportPaysButton);

        dataPanel.add(filterPanel);
        dataPanel.add(receivePanel);
        dataPanel.add(causeTableScrollPane);
        dataPanel.add(causePanel);
        dataPanel.add(exportPanel);

        JPanel buttonPanel = new JPanel();

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });



        buttonPanel.add(okButton);

        mainPanel.add(dataPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
    }

    public Date getFromDate() {
        return fromDateField.getDate();
    }

    public Date getToDate() {
        return toDateField.getDate();
    }

    public void updateCauses() {
        causeTableModel.updateCauses();
    }

    class CauseTableModel extends AbstractTableModel {
        private final java.util.List<Cause> causes;

        CauseTableModel() {
            causes = controller.getCauses();
        }

        void updateCauses() {
            fireTableDataChanged();
        }

        Cause getCauseAt(int index) {
            return causes.get(index);
        }

        @Override
        public int getRowCount() {
            return causes.size();
        }

        @Override
        public int getColumnCount() {
            return 7;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Cause cause = causes.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return rowIndex+1;
                case 1:
                    return cause.getRegnum();
                case 2:
                    return cause.getBeginDate();
                case 3:
                    return cause.getStartDate();
                case 4:
                    return cause.getEstimateDate();
                case 5:
                    return cause.getProcId();
                case 6:
                    return cause.getTotalCharge();
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "№";
                case 1:
                    return "Рег номер";
                case 2:
                    return "От";
                case 3:
                    return "Начало";
                case 4:
                    return "Оцен оконч";
                case 5:
                    return "Процедура";
                case 6:
                    return "Пошлина";
                default:
                    return super.getColumnName(column);
            }
        }
    }

    class ApplicantsListModel extends AbstractListModel<String> {
        List<Applicant> applicants;

        ApplicantsListModel() {
            applicants = new ArrayList<Applicant>();
        }

        void setApplicants(List<Applicant> applicants) {
            this.applicants.clear();
            this.applicants.addAll(applicants);
            fireContentsChanged(this,0,applicants.size()-1);
        }

        @Override
        public int getSize() {
            return applicants.size();
        }

        @Override
        public String getElementAt(int index) {
            Applicant applicant = applicants.get(index);
            return applicant.getName();
        }
    }
}

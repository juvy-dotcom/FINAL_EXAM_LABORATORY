package ui;

import controller.TaskManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import model.Task;

public class TaskForm extends JFrame {
    private static boolean isFormOpen = false;
    private final TaskManager taskManager;
    private final MainWindow mainWindow;
    private JTextField taskIdField;
    private JTextField taskNameField;
    private JTextArea taskDescriptionArea;
    private JComboBox<String> statusComboBox;
    private JButton saveButton;

    public TaskForm(TaskManager taskManager, MainWindow mainWindow) {
        this.taskManager = taskManager;
        this.mainWindow = mainWindow;
        isFormOpen = true;
        initializeUI();
    }

    public static boolean isFormOpen() {
        return isFormOpen;
    }

    private void initializeUI() {
        setTitle("Add Task");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Task ID
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Task ID:"), gbc);
        gbc.gridx = 1;
        taskIdField = new JTextField(taskManager.generateTaskId());
        taskIdField.setEditable(false);
        add(taskIdField, gbc);

        // Task Name
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Task Name:"), gbc);
        gbc.gridx = 1;
        taskNameField = new JTextField(20);
        add(taskNameField, gbc);

        // Task Description
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Task Description:"), gbc);
        gbc.gridx = 1;
        taskDescriptionArea = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(taskDescriptionArea);
        add(scrollPane, gbc);

        // Status
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        statusComboBox = new JComboBox<>(new String[]{"Not Started", "Ongoing", "Done"});
        statusComboBox.setSelectedIndex(0); // Default to Not Started
        add(statusComboBox, gbc);

        // Save Button
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        saveButton = new JButton("Save Task");
        saveButton.addActionListener((ActionEvent e) -> {
            saveTask();
        });
        add(saveButton, gbc);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                isFormOpen = false;
            }
        });

        setVisible(true);
    }

    private void saveTask() {
        String taskId = taskIdField.getText();
        String taskName = taskNameField.getText().trim();
        String taskDescription = taskDescriptionArea.getText().trim();
        String status = (String) statusComboBox.getSelectedItem();

        // Validations
        if (taskName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Task Name cannot be empty.");
            return;
        }
        if (taskDescription.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Task Description cannot be empty.");
            return;
        }

        // Map status to enum
        Task.Status taskStatus;
        switch (status) {
            case "Ongoing":
                taskStatus = Task.Status.ONGOING;
                break;
            case "Done":
                taskStatus = Task.Status.DONE;
                break;
            default:
                taskStatus = Task.Status.NOT_STARTED;
        }

        // Create and add task
        Task task = new Task(taskId, taskName, taskDescription, taskStatus);
        taskManager.addTask(task);

        // Refresh main window and close form
        mainWindow.refreshTable();
        isFormOpen = false;
        dispose();
    }
}

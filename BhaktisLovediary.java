import javax.swing.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

 class BhaktisLoveDiary {

    private static final String PASSWORD_FILE = "password.txt";
    private static final String[] loveQuotes = {
            "You are my sunshine in the darkest days. 🌞",
            "With you, every moment feels magical. ✨",
            "I didn’t believe in fate until I met you. 💫",
            "Your smile is my favorite sight. 😊",
            "You and I — written in the stars. 🌠",
            "Every love story is beautiful, but ours is my favorite. ❤️"
    };

    public static void main(String[] args) {
        // Start background music
        playMusic("love.wav");

        // Setup main window
        JFrame frame = new JFrame("Bhakti's Love Diary 💖");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Set custom window icon
        try {
            Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
            frame.setIconImage(icon);
        } catch (Exception e) {
            System.out.println("Icon not found.");
        }

        // Welcome panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(new Color(255, 192, 203));
        JLabel welcomeLabel = new JLabel("Welcome, Bhakti ❤️");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        welcomePanel.add(welcomeLabel);
        frame.add(welcomePanel, BorderLayout.NORTH);

        // Main content panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(255, 240, 245));
        frame.add(centerPanel, BorderLayout.CENTER);

        // Quote of the Day
        JLabel quoteLabel = new JLabel(getRandomQuote());
        quoteLabel.setFont(new Font("SansSerif", Font.ITALIC, 18));
        quoteLabel.setForeground(new Color(138, 43, 226));
        quoteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(quoteLabel);

        // Memory writing area
        JTextArea memoryArea = new JTextArea(8, 40);
        memoryArea.setFont(new Font("Serif", Font.PLAIN, 16));
        memoryArea.setLineWrap(true);
        memoryArea.setWrapStyleWord(true);
        JScrollPane memoryScroll = new JScrollPane(memoryArea);
        memoryScroll.setBorder(BorderFactory.createTitledBorder("Write a Sweet Memory 💌"));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(memoryScroll);

        // Save button with updated save to Documents folder
        JButton saveBtn = new JButton("Save Memory");
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtn.addActionListener(e -> {
            String memory = memoryArea.getText().trim();
            if (!memory.isEmpty()) {
                File dir = new File(System.getProperty("user.home") + "/Documents/BhaktiDiary");
                if (!dir.exists()) dir.mkdirs();

                File file = new File(dir, "memories.txt");
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write("❤️ " + memory + "\n\n");
                    JOptionPane.showMessageDialog(frame, "Memory saved to: " + file.getAbsolutePath());
                    memoryArea.setText("");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Failed to save memory!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please write something first!");
            }
        });
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(saveBtn);

        // Secret message unlock button
        JButton secretBtn = new JButton("Unlock Secret Message 🔐");
        secretBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        secretBtn.addActionListener(e -> handleSecretMessage(frame));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(secretBtn);

        frame.setVisible(true);
    }

    // Play looping background music from .wav file
    public static void playMusic(String filePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // keep playing
            clip.start();
        } catch (Exception e) {
            System.out.println("Music file not found or failed to play.");
        }
    }

    // Return a random love quote
    private static String getRandomQuote() {
        Random rand = new Random();
        return "💬 Quote of the Day: " + loveQuotes[rand.nextInt(loveQuotes.length)];
    }

    // Handle password creation and secret message unlocking
    private static void handleSecretMessage(JFrame frame) {
        try {
            File passwordFile = new File(PASSWORD_FILE);

            if (!passwordFile.exists()) {
                String newPassword = JOptionPane.showInputDialog(frame,
                        "Set your secret password (first time only):");
                if (newPassword != null && !newPassword.isEmpty()) {
                    try (FileWriter writer = new FileWriter(passwordFile)) {
                        writer.write(newPassword);
                    }
                    JOptionPane.showMessageDialog(frame,
                            "Password saved! Use it to unlock the message next time. 🔐");
                } else {
                    JOptionPane.showMessageDialog(frame, "Password can't be empty!");
                }
                return;
            }

            // Existing password check
            String savedPassword = new BufferedReader(new FileReader(passwordFile)).readLine();
            String input = JOptionPane.showInputDialog(frame, "Enter your secret password:");
            if (input != null && input.equals(savedPassword)) {
                JTextArea secretMsg = new JTextArea(
                        "Dear Bhakti,\n\n" +
                                "You're not just someone I love — you're my person, my partner, my forever. 💍\n" +
                                "No app, no words, no code could ever fully show how much you mean to me.\n\n" +
                                "❤️ Yours, always."
                );
                secretMsg.setLineWrap(true);
                secretMsg.setWrapStyleWord(true);
                secretMsg.setEditable(false);
                secretMsg.setFont(new Font("Serif", Font.PLAIN, 16));
                JScrollPane scrollPane = new JScrollPane(secretMsg);
                scrollPane.setPreferredSize(new Dimension(400, 200));
                JOptionPane.showMessageDialog(frame, scrollPane, "Secret Message 💌", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong password 😅", "Access Denied", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error reading password file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

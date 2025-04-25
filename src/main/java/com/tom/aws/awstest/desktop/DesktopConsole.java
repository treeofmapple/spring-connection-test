package com.tom.aws.awstest.desktop;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.formdev.flatlaf.FlatDarkLaf;
import com.tom.aws.awstest.common.ServiceLogger;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DesktopConsole {

	// fix console
	
	// fix to put it there to generate data on a button instead everytime the systems starts
	
	// and show also on both logs either on normal and the another
	
	// make it the desktop console show first asking the variables to be inserted 
	
	private final ConfigurableApplicationContext context;

	private JEditorPane textPane;
	private JFrame frame;
	private SystemTray systemTray;
	private TrayIcon trayIcon;
	private Image trayIconImage;
	private final StringBuilder logBuffer = new StringBuilder();

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady() {
		System.out.println("📢 Is headless: " + GraphicsEnvironment.isHeadless());

		if (!GraphicsEnvironment.isHeadless()) {
			SwingUtilities.invokeLater(this::createConsoleWindow);
		} else {
			System.out.println("⚠️ Headless environment — GUI not supported.");
		}
	}

	private void createConsoleWindow() {
		try {
			UIManager.setLookAndFeel(new FlatDarkLaf());
		} catch (Exception e) {
			e.printStackTrace();
		}

		frame = new JFrame("Spring Boot App Console");
		frame.setSize(1200, 500);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		textPane = new JEditorPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		textPane.setBackground(new Color(43, 43, 43));
		textPane.setForeground(Color.WHITE);
		textPane.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textPane.setText("<html><body style='font-family:monospace; color:white;'></body></html>");

		JScrollPane scrollPane = new JScrollPane(textPane);

		// ✅ Create toolbar panel for buttons
		frame.add(createTopToolbar(), BorderLayout.NORTH);

		/*
		 * 
		stayOnTopButton.addActionListener(e -> {
			boolean currentState = frame.isAlwaysOnTop();
			frame.setAlwaysOnTop(!currentState);
			stayOnTopButton.setText(currentState ? "📌 Stay on Top" : "📌 Disable Top");
		});

		topPanel.add(stayOnTopButton);

		// ✅ Add shortcut: Ctrl+T toggles always-on-top
		KeyStroke toggleTopKey = KeyStroke.getKeyStroke("control T");
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
			.put(toggleTopKey, "toggleAlwaysOnTop");
		frame.getRootPane().getActionMap().put("toggleAlwaysOnTop", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean currentState = frame.isAlwaysOnTop();
				frame.setAlwaysOnTop(!currentState);
				stayOnTopButton.setText(currentState ? "📌 Stay on Top" : "📌 Disable Top");
			}
		});

		*/
		
		// ✅ Layout
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		// ✅ Load tray icon if available
		loadTrayIconImage();
		if (trayIconImage != null) {
			frame.setIconImage(trayIconImage);
		}

		// ✅ Dock to bottom-right of the screen
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width - frame.getWidth() - 20;
		int y = screenSize.height - frame.getHeight() - 60;
		frame.setLocation(x, y);

		frame.setVisible(true);

		redirectSystemStreams();
		createSystemTray();

		System.out.println("✅ Log redirection initialized.");
	}

	private JPanel createTopToolbar() {
	    JPanel toolbar = new JPanel();
	    toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));
	    toolbar.setBackground(new Color(60, 63, 65)); // IntelliJ-style dark gray
	    toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(80, 80, 80)));

	    JButton stayOnTopButton = createStyledButton("📌", "Stay on Top");
	    JButton minimizeButton = createStyledButton("➖", "Minimize");
	    JButton exitButton = createStyledButton("❌", "Exit");

	    // Add some spacing
	    toolbar.add(Box.createHorizontalStrut(8));
	    toolbar.add(stayOnTopButton);
	    toolbar.add(Box.createHorizontalStrut(8));
	    toolbar.add(minimizeButton);
	    toolbar.add(Box.createHorizontalStrut(8));
	    toolbar.add(exitButton);
	    toolbar.add(Box.createHorizontalGlue()); // Push buttons to the left

	    return toolbar;
	}

	private JButton createStyledButton(String iconText, String tooltip) {
	    JButton button = new JButton(iconText);
	    button.setToolTipText(tooltip);
	    button.setFocusPainted(false);
	    button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
	    button.setContentAreaFilled(false);
	    button.setOpaque(true);
	    button.setBackground(new Color(60, 63, 65));
	    button.setForeground(Color.WHITE);
	    button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
	    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	    // Hover effect
	    button.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseEntered(MouseEvent e) {
	            button.setBackground(new Color(75, 110, 175));
	        }

	        @Override
	        public void mouseExited(MouseEvent e) {
	            button.setBackground(new Color(60, 63, 65));
	        }
	    });

	    // Example ActionListener — replace with your actual logic
	    button.addActionListener(e -> {
	        System.out.println(tooltip + " clicked");
	        if (tooltip.equals("Exit")) System.exit(0);
	    });

	    return button;
	}

	
	private void loadTrayIconImage() {
		try {
			URL iconUrl = getClass().getResource("/icon/tray-icon.png");
			if (iconUrl == null) {
				System.err.println("❌ Tray icon image not found at /icon/tray-icon.png");
				return;
			}
			trayIconImage = ImageIO.read(iconUrl);
			System.out.println("✅ Tray icon loaded: " + iconUrl);
		} catch (IOException e) {
			System.err.println("❌ Failed to load tray icon image: " + e.getMessage());
		}
	}

	private void createSystemTray() {
		if (!SystemTray.isSupported()) {
			System.out.println("⚠️ SystemTray not supported on this platform.");
			return;
		}

		systemTray = SystemTray.getSystemTray();
		trayIcon = new TrayIcon(trayIconImage, "Spring Boot App Console");
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("Spring Boot Console");

		// ❌ Don't use AWT PopupMenu
		// trayIcon.setPopupMenu(...);

		// ✅ Handle click to show custom menu
		trayIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) {
					SwingUtilities.invokeLater(() -> showTrayMenu(e.getX(), e.getY()));
				}
			}
		});

		try {
			systemTray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	private void showTrayMenu(int x, int y) {
	    SwingUtilities.invokeLater(() -> {
	        JPopupMenu popup = new JPopupMenu();

	        JMenuItem openItem = new JMenuItem("🖥 Open Console");
	        openItem.addActionListener(e -> {
	            frame.setVisible(true);
	            frame.setExtendedState(JFrame.NORMAL);
	            frame.toFront();
	        });

	        JMenuItem stayOnTopItem = new JMenuItem(frame.isAlwaysOnTop() ? "📌 Disable Top" : "📌 Stay on Top");
	        stayOnTopItem.addActionListener(e -> {
	            boolean currentState = frame.isAlwaysOnTop();
	            frame.setAlwaysOnTop(!currentState);
	            stayOnTopItem.setText(currentState ? "📌 Stay on Top" : "📌 Disable Top");
	        });

	        JMenuItem stopAppItem = new JMenuItem("🛑 Stop App");
	        stopAppItem.addActionListener(e -> {
	            ServiceLogger.info("Tray: Stop App requested.");
	            new Thread(() -> {
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException ignored) {}
	                context.close();
	            }).start();
	        });

	        JMenuItem exitItem = new JMenuItem("❌ Exit Console");
	        exitItem.addActionListener(e -> {
	            if (systemTray != null && trayIcon != null) {
	                systemTray.remove(trayIcon);
	            }
	            frame.dispose();
	        });

	        Font font = new Font("Segoe UI", Font.PLAIN, 14);
	        for (JMenuItem item : new JMenuItem[]{openItem, stayOnTopItem, stopAppItem, exitItem}) {
	            item.setFont(font);
	        }

	        popup.add(openItem);
	        popup.add(stayOnTopItem);
	        popup.add(stopAppItem);
	        popup.addSeparator();  // 👈 This adds the separator line
	        popup.add(exitItem);

	        // ✅ Create a temporary, invisible owner for positioning
	        JFrame invisibleFrame = new JFrame();
	        invisibleFrame.setUndecorated(true);
	        invisibleFrame.setType(JFrame.Type.UTILITY);
	        invisibleFrame.setAlwaysOnTop(true);
	        invisibleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        invisibleFrame.setSize(0, 0);
	        invisibleFrame.setLocation(x, y);
	        invisibleFrame.setVisible(true);

	        popup.show(invisibleFrame, 0, 0);

	        // Dispose frame after popup closes
	        popup.addPopupMenuListener(new PopupMenuListener() {
	            @Override public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
	            @Override public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
	                invisibleFrame.dispose();
	            }
	            @Override public void popupMenuCanceled(PopupMenuEvent e) {
	                invisibleFrame.dispose();
	            }
	        });
	    });
	}

	private void redirectSystemStreams() {
		OutputStream textAreaStream = new OutputStream() {
			@Override
			public void write(int b) {
				appendToTextArea(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) {
				appendToTextArea(new String(b, off, len));
			}
		};

		PrintStream ps = new PrintStream(textAreaStream, true);
		System.setOut(ps);
		System.setErr(ps);
	}

	private void appendToTextArea(final String text) {
		SwingUtilities.invokeLater(() -> {
			String coloredText = applyColorPattern(text).replaceAll("\n", "<br>");
			logBuffer.append(coloredText).append("<br>");

			textPane.setText("<html><body style='font-family:monospace; color:white;'>" + logBuffer.toString()
					+ "</body></html>");

			textPane.setCaretPosition(textPane.getDocument().getLength());
		});
	}

	private String applyColorPattern(String text) {
		String levelPattern = "(INFO|ERROR|WARN|DEBUG|TRACE)";
		Pattern pattern = Pattern.compile(".*?(" + levelPattern + ").*");
		Matcher matcher = pattern.matcher(text);

		if (matcher.find()) {
			String level = matcher.group(1);
			switch (level) {
			case "INFO":
				text = text.replace(level, "<font color='green'>" + level + "</font>");
				break;
			case "ERROR":
				text = text.replace(level, "<font color='red'>" + level + "</font>");
				break;
			case "WARN":
				text = text.replace(level, "<font color='orange'>" + level + "</font>");
				break;
			case "DEBUG":
				text = text.replace(level, "<font color='blue'>" + level + "</font>");
				break;
			}
		}

		text = text.replaceFirst("com\\.tom\\.aws\\.awstest\\.desktop",
				"<font color='cyan'>com.tom.aws.awstest.desktop</font>");

		return text;
	}

	@PreDestroy
	public void closeConsole() {
		if (frame != null) {
			frame.dispose();
			ServiceLogger.info("Console window closed.");
		}

		if (systemTray != null && trayIcon != null) {
			systemTray.remove(trayIcon);
		}
	}
}

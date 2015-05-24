package downloadGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;

import ExcelExporter.ExcelExporter;
import Logger.Logger;
import ShipwireAPI.ShipwireAPIClient;
import ShopifyAPI.CredentialShopify;
import ShopifyAPI.Order;
import ShopifyAPI.ShopifyAPIClient;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.swing.JScrollPane;

import java.awt.ScrollPane;

import javax.swing.ScrollPaneConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class DownloadGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtStoreName;
	private JTextField txtAPIKey;
	private JTextField txtPassword;
	private JTextField txtSharedSecret;
	private JTextArea txtoutputPath;
	private JTextArea txtArea;
	private Logger logger;
	private List<String> data;
	private JDatePickerImpl datePickerFrom;
	private JDatePickerImpl datePickerTo;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DownloadGUI frame = new DownloadGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DownloadGUI() {
		setTitle("Data extractor");
		data = new ArrayList<String>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 789, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtStoreName = new JTextField();
		txtStoreName.setBounds(12, 33, 116, 22);
		contentPane.add(txtStoreName);
		txtStoreName.setColumns(10);
		
		txtAPIKey = new JTextField();
		txtAPIKey.setBounds(169, 33, 240, 22);
		contentPane.add(txtAPIKey);
		txtAPIKey.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(167, 88, 242, 22);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		JButton btnGetOrders = new JButton("Download");
		btnGetOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConnectToShopify();	
				//ConnectToShipwire();
			}
		});
		btnGetOrders.setBounds(12, 182, 97, 25);
		contentPane.add(btnGetOrders);
		
		txtArea = new JTextArea();
		txtArea.setBounds(36, 166, 689, 364);
		txtArea.setLineWrap(true);
		
		JLabel lblNewLabel = new JLabel("Store Name:");
		lblNewLabel.setBounds(12, 13, 74, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblApiKey = new JLabel("API Key:");
		lblApiKey.setBounds(170, 13, 56, 16);
		contentPane.add(lblApiKey);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(167, 68, 74, 16);
		contentPane.add(lblPassword);
		
		JLabel lblSharedSecret = new JLabel("Shared Secret:");
		lblSharedSecret.setBounds(169, 123, 97, 16);
		contentPane.add(lblSharedSecret);
		
		txtSharedSecret = new JTextField();
		txtSharedSecret.setColumns(10);
		txtSharedSecret.setBounds(169, 142, 240, 22);
		contentPane.add(txtSharedSecret);		
		
		populateDefaultValues(GetDefaultCredential());
		this.logger = new Logger(this.txtArea);
		
		JButton btnExportData = new JButton("Export to Excel");
		btnExportData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ExportToExcel();
			}
		});
		btnExportData.setBounds(12, 665, 164, 25);
		contentPane.add(btnExportData);		
		
		JScrollPane scrollPane = new JScrollPane(txtArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 220, 689, 364);
				
		contentPane.add(scrollPane);
		
		JLabel lblOutputPath = new JLabel("Output path:");
		lblOutputPath.setBounds(12, 636, 74, 16);
		contentPane.add(lblOutputPath);
		
		txtoutputPath = new JTextArea();
		txtoutputPath.setEditable(false);
		txtoutputPath.setBounds(94, 633, 495, 22);
		contentPane.add(txtoutputPath);
		
		JButton btnBrowse = new JButton("Browse ...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showFileChooser();
			}
		});
		btnBrowse.setBounds(601, 632, 97, 25);
		contentPane.add(btnBrowse);

		UtilDateModel modelFrom = new UtilDateModel();
        
		Properties p = new Properties();
		p.put("test.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		
		JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelFrom, p);
        this.datePickerFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter());
        datePanelFrom.setBounds(201, 432, 97, 25);
        datePickerFrom.setBounds(517, 33, 142, 25);
		contentPane.add(datePickerFrom);

		UtilDateModel modelTo = new UtilDateModel();
		
		Properties p2 = new Properties();
		p2.put("test.today", "Today");
		p2.put("text.month", "Month");
		p2.put("text.year", "Year");
		
		JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo, p2);
        this.datePickerTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter());
        datePanelTo.setBounds(101, 432, 97, 25);
        datePickerTo.setBounds(517, 96, 142, 25);
		contentPane.add(datePickerTo);
		
		JLabel lblDateFrom = new JLabel("Date from:");
		lblDateFrom.setBounds(438, 36, 67, 16);
		contentPane.add(lblDateFrom);
		
		JLabel lblDateTo = new JLabel("Date to:");
		lblDateTo.setBounds(438, 96, 56, 16);
		contentPane.add(lblDateTo);
		
	}
	
	public class DateLabelFormatter extends AbstractFormatter {

	    private String datePattern = "yyyy-MM-dd";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	    }

	}
	
	private void showFileChooser()
	{
		final JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(false);	
		fc.setDialogTitle("Select an xls doc, or create a new one");
		FileFilter filter = new FileNameExtensionFilter("Microsoft Excel 97-2003 file", "xls");
		fc.setFileFilter(filter);
		
		int rval = fc.showOpenDialog(this.contentPane);
		if (rval == JFileChooser.APPROVE_OPTION)
		{
			this.txtoutputPath.setText(fc.getSelectedFile().getAbsolutePath());
		}				
	}
	
	private void populateDefaultValues(CredentialShopify cred)
	{
		txtAPIKey.setText(cred.getApiKey());
		txtPassword.setText(cred.getPassword());
		txtSharedSecret.setText(cred.getSharedSecret());
		txtStoreName.setText(cred.getShopName());
	}
	
	private CredentialShopify GetDefaultCredential()
	{
		return new CredentialShopify("",
				"", 
				"", 
				"");
	}
	
	private CredentialShopify GetCredentialFromForm()
	{		
		return new CredentialShopify(txtAPIKey.getText(),
				txtSharedSecret.getText(),
				txtStoreName.getText(),
				txtPassword.getText());
	}
	
	private void ConnectToShopify()
	{
		ShopifyAPIClient client = new ShopifyAPIClient(GetCredentialFromForm(), this.logger);
		HashMap<Integer, Order> orders;
		try {
			orders = client.getOrders();
			this.logger.Log(orders);
		} catch (IOException e) {
			this.logger.Log("Error: " + e.getMessage());		
		}				
	}
	
	private void ConnectToShipwire()
	{	
		ShipwireAPIClient client = new ShipwireAPIClient(this.logger, "tomme1982@gmail.com", "TommeAsh");
		List<String> infos;
		//client.GetInfoFromShipWire();				
		try {
			client.GetInfo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void ExportToExcel()
	{
		//if (okToExportToExcel())
		ExcelExporter exporter = new ExcelExporter(this.logger);
		exporter.Export(txtoutputPath.getText());
	}
	
	private boolean okToExportToExcel()
	{
		return data.size() > 0 && !txtoutputPath.getText().isEmpty();
	}
}

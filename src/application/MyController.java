package application;

import java.net.URL;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MyController implements Initializable{
	@FXML
	private Button myButton;
	
	@FXML
	private Button myButton1;

	@FXML
	private TextField myTextField1;
	
	@FXML
	private TextField myTextField2;
	
	@FXML
	private PasswordField password;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO (don't really need to do anything here).

	}

	// When user click on myButton
	// this method will be called.
	public void showPassword(ActionEvent event) {
		System.out.println("Button Clicked!");
		String name = myTextField1.getText();
		String key = getFixLength(password.getText());
		// Show in VIEW
        myTextField2.setText(getPassword(name, key));
	}


	public void show6(ActionEvent event) {
//		System.out.println("Button Clicked!");
		String name = myTextField1.getText();
//		System.out.println(password.getText()+"****");
		String key = getFixLength(password.getText());
		// Show in VIEW
        myTextField2.setText(get6digit(getPassword(name, key)));
	}
	
	public String getPassword(String src, String key) {
		  try {
	            //生成key
	            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
	            keyGenerator.init(new SecureRandom());
	            byte[] key1 = Arrays.copyOf(key.getBytes(), 16);
//	            System.out.println(Arrays.toString(key1));
	            //key转换为密钥
	            Key key2 = new SecretKeySpec(key1, "AES");
	            
	            //加密
	            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5padding");
	            cipher.init(Cipher.ENCRYPT_MODE, key2);
	            byte[] result = cipher.doFinal(src.getBytes());
//	            System.out.println("jdkAES加密: "+Hex.encodeHexString(result));  //转换为十六进制
	            
	            return byte2hex(result).substring(0, 12);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "error";
	        }
	}
	
	public String getFixLength(String key) {
		if(key == null) {
			return "1111111111111111";
		}
		if(key.length() >= 16) {
			return key.substring(0, 16);
		}else {
			return String.format("%16s", key);
		}
	}
	
	public static String get6digit(String str) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) < 'a') {
				sb.append(str.charAt(i));
			}
		}
		if(sb.length() < 6) {
			sb.append("000000");
		}
		return sb.toString().substring(0, 6);
	}
	
	public String byte2hex(byte[] a) {
        String hexString = "";
        for(int i = 0; i < a.length; i++){
            String thisByte = "".format("%x", a[i]);
            hexString += thisByte;
        }
        return hexString;
    }
	
}

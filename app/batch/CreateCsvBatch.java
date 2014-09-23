package batch;

import java.io.File;
import java.io.PrintWriter;

public class CreateCsvBatch {

	public static void main(String[] args) throws Exception {
		// user
		{
			PrintWriter pw = new PrintWriter(new File("/Users/a12688/user.csv"));
			for (int i = 1; i <= 10000; i++) {
				pw.println(i + ",name_" + i + ",mail_" + i + ",password_" + i);
			}
			pw.flush();
			pw.close();
		}
		// article
		{
			PrintWriter pw = new PrintWriter(new File("/Users/a12688/article.csv"));
			String data = "";
			for (int i = 0; i < 10; i++) {
				data += "あいうえおあいうえおあいうえおあいうえおあいうえおあいうえおあいうえおあいうえおあいうえおあいうえお";
			}
			int count = 1;
			for (int i = 1; i <= 10000; i++) {
				for (int j = 1; j <= 20; j++) {
					pw.println(count + "," + i + ",あいうえお," + data + ",2014-09-13 00:00:00,2014-09-13 00:00:00");
					count++;
				}
			}
			pw.flush();
			pw.close();
		}
	}
}

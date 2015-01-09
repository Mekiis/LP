import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogPuzzleSkeleton {

	private static List<URL> read_urls(String filename) {
		/*
		 * Returns a list of the puzzle urls from the given log file, extracting
		 * the hostname from the filename itself. Screens out duplicate urls and
		 * returns the urls sorted into increasing order.
		 */
		String base_url = extractBaseUrlFromFileName(filename);

		//TODO: Regex pour extraire les bonnes url
		Pattern urlPattern = Pattern.compile("\\S*puzzle\\S*.jpg");

		List<URL> fullUrls = null;

		BufferedReader f = null;
		
		try {
			f = new BufferedReader(new FileReader(new File(filename)));
			List<String> urls = findAllUrlsMatchingPattern(urlPattern, f);
			urls = removeDuplicates(urls);
			sortUrlList(urls);
			fullUrls = formatFullUrls(base_url, urls);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			close(f);
		}

		return fullUrls;
	}

	private static String extractBaseUrlFromFileName(String filename) {
		String baseURL = "";
		Pattern pattern = Pattern.compile("(?<=_)\\S*");
		Matcher matcher = pattern.matcher(filename);
		if (matcher.find()) {
			baseURL = "https://" + matcher.group();
			System.out.println("Base URL found : "+baseURL);
		} else {
		    System.out.println("Error - No base URL found in : "+filename);
		}
		
		return baseURL;
	}

	private static List<String> findAllUrlsMatchingPattern(Pattern urlPattern, BufferedReader f)
			throws IOException {
		String line;
		List<String> urls = new ArrayList<String>();
		while ((line = f.readLine()) != null) {
			Matcher matcher = urlPattern.matcher(line);
			if (matcher.find()) {
				System.out.println("URL found : "+matcher.group());
				urls.add(matcher.group());
			}
	    }
		return urls;
	}

	private static List<String> removeDuplicates(List<String> list) {
		Set<String> listNoDuplicateHashSet = new LinkedHashSet<>(list);
		List<String> listClear = new ArrayList<>(listNoDuplicateHashSet);
		return listClear;
	}

	private static void sortUrlList(List<String> urls) {
		Collections.sort(urls, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				Pattern pattern = Pattern.compile("[\\w]{4}.jpg");
				Matcher matchero1 = pattern.matcher(o1);
				Matcher matchero2 = pattern.matcher(o2);
				if (matchero1.find()) {
					o1 = matchero1.group();
				}
				if(matchero2.find()){
					o2 = matchero2.group();
				}
				return o1.compareTo(o2);
			}
		});
	}

	private static List<URL> formatFullUrls(String baseUrl, List<String> urls)
			throws MalformedURLException {
		List<URL> fullUrls = new ArrayList<URL>();
		for (String urlString : urls) {
			fullUrls.add(new URL(baseUrl + urlString));
		}
		return fullUrls;
	}

	private static void downloadImages(List<URL> imgUrls, String destDirName) {

		// Create destination directory if it doesn't exist
		File destDir = new File(destDirName);
		if (!destDir.exists())
			destDir.mkdir();

		// Create html file
		Path htmlFilePath = Paths.get(destDirName + "index.html");

		PrintWriter htmlFile = null;
		
		try {
			htmlFile = new PrintWriter(Files.newBufferedWriter(htmlFilePath,
					StandardCharsets.UTF_8, StandardOpenOption.CREATE));
			htmlFile.println("<html><body>");

			int i = 0;
			for (URL url : imgUrls) {
				i++;
				String imgFileName = "img" + i + ".jpg";
				htmlFile.print("<img src=\"" + imgFileName + "\">");
				Path destinationPath = Paths.get(destDirName + imgFileName);
				System.out.println(url);
				if (!destinationPath.toFile().exists())
					Files.copy(url.openStream(), destinationPath,
							StandardCopyOption.REPLACE_EXISTING);
			}

			htmlFile.println("</body></html>");

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(htmlFile);
		}

	}

	public static void close(Closeable c) {
	     if (c == null) return; 
	     try {
	         c.close();
	     } catch (IOException e) {
	         //log the exception
	     }
	}


	public static void main(String[] args) {
		List<URL> urlsToDownload = read_urls("place_code.google.com");
		//TODO: Compléter avec le chemin du dossier destination
		downloadImages(urlsToDownload, "images/");
	}

}

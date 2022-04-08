package uk.ac.abertay.s1902765.nexttrain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.builder.XmlDocument;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class BuildDatabaseActivity extends AppCompatActivity {

    public static AsyncTask<Void, Void, Void> mTask;
    public static String serverToken = null;
    public static Document stationsDoc = null;

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_database);


        new Thread(new Runnable() {
            @Override
            public void run() {

                switch (getServerToken()){
                    case 0: {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Fetched server token", Toast.LENGTH_SHORT).show();
                            }
                        });
                        try {
                            downloadStationsXML();
                            stationsDoc = loadStationsFromStorage();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Operation completed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (IOException e) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Error downloading stations data", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                            finish();
                        } catch (SAXException e) {
                            e.printStackTrace();
                            finish();
                        }
                    }
                    break;
                    case -1: {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Incorrect server authentication", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    break;
                    case -2: {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    break;
                    case -3: {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "An exception occurred", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    break;
                    default: {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Something went wrong when fetching server token", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    break;

                }

            }
        }).start();

    }


    /**
     * Fetches the stations.xml document from local storage if there is one present
     * @return XML document from local storage, Null if one was not present
     */
    protected Document loadStationsFromStorage() throws IOException, SAXException, ParserConfigurationException {

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        ArrayList temp = null;
        List<HashMap<Object, Object>> stationList = null;
        try {
            saxParser = saxParserFactory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                String currentValue = "";
                boolean currentElement = false;
                HashMap<Object, Object> station;
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    currentElement = true;
                    currentValue = "";
                    if (localName.equals("Station")) {
                        station = new HashMap<>();
                    }
                }

                public void endElement(String uri, String localName, String qName) throws SAXException {
                    currentElement = false;
                    if (localName.equalsIgnoreCase("CrsCode"))
                        station.put("CrsCode", currentValue);
                    else if (localName.equalsIgnoreCase("AlternativeIdentifiers"))
                        station.put("AlternativeIdentifiers", currentValue);
                    else if (localName.equalsIgnoreCase("NationalLocationCode"))
                        station.put("NationalLocationCode", currentValue);
                    else if (localName.equalsIgnoreCase("Name"))
                        station.put("Name", currentValue);
                    else if (localName.equalsIgnoreCase("SixteenCharacterName"))
                        station.put("SixteenCharacterName", currentValue);
                    else if (localName.equalsIgnoreCase("Longitude"))
                        station.put("Longitude", currentValue);
                    else if (localName.equalsIgnoreCase("Latitude"))
                        station.put("Latitude", currentValue);
                    else if (localName.equalsIgnoreCase("StationOperator"))
                        station.put("StationOperator", currentValue);
                    else if (localName.equalsIgnoreCase("Station"))
                        stationList.add(station);
                }
            };

            File fs=getApplicationContext().getFilesDir();
            File stationsFile = new File(fs.getAbsoluteFile(),"stations.xml");
            saxParser.parse(stationsFile, handler);
            //This doesn't work still
            //TODO - Fix this shit

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "stations.xml read from filesystem", Toast.LENGTH_SHORT).show();
                TextView helperText = findViewById(R.id.buildingDatabase_currentActionTextView);
                helperText.setText("Parsing stations xml data");
            }
        });

        for (HashMap<Object, Object> stationItem : stationList) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), stationItem.get("Name").toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return null;
    }

    /**
     * Fetches the stations.xml document from the national rail server and stores it on device
     * @return Boolean: True if document was downloaded, false if not
     */
    protected boolean downloadStationsXML() throws IOException {
        if (serverToken!= null){
            URL url = null;
            try {
                url = new URL("https://opendata.nationalrail.co.uk/api/staticfeeds/4.0/stations");
            } catch (MalformedURLException malformedURLException) {
                malformedURLException.printStackTrace();
            }
            HttpURLConnection http = null;
            try {
                http = (HttpURLConnection) url.openConnection();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            http.setRequestProperty("X-Auth-Token", serverToken);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    TextView helperText = findViewById(R.id.buildingDatabase_currentActionTextView);
                    helperText.setText("Sending request for stations data");
                }
            });


            if (http.getResponseCode() == HttpURLConnection.HTTP_OK){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LinearProgressIndicator progressIndicator = findViewById(R.id.buildingDatabase_progressIndicator);
                        progressIndicator.setIndeterminate(false);
                        progressIndicator.setProgress(0);
                    }
                });

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView helperText = findViewById(R.id.buildingDatabase_currentActionTextView);
                        helperText.setText("Storing stations data");
                    }
                });

                try {
                    FileOutputStream fos = new FileOutputStream(new File(getFilesDir(), "stations.xml"));
                    BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
                    String line;

                    //float exactProgress = 0.0F;
                    //float exactProgressInterval = 100 / br.lines().count();
                    //int currentProgress = 0;

                    for (line = br.readLine(); line != null; line = br.readLine()) {
                        fos.write(line.getBytes(StandardCharsets.UTF_8));
                    }
                    fos.flush();
                    fos.close();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            LinearProgressIndicator progressIndicator = findViewById(R.id.buildingDatabase_progressIndicator);
                            progressIndicator.setProgress(0);
                            progressIndicator.setMax(100);
                            Toast.makeText(getApplicationContext(), "Downloaded stations.xml", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Exception occurred", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return false;
                }
            }
            else{
                return false;
            }

        }
        else{
            return false;
        }
    }

    /**
     * Function to get a session token from the national rail server
     * Returns 0 when token generation is successful
     * -1 when authentication is incorrect
     * -2 when there is a server error
     * -3 when there is a general exception
     * @return
     */
    protected int getServerToken(){

        handler.post(new Runnable() {
            @Override
            public void run() {
                TextView helperText = findViewById(R.id.buildingDatabase_currentActionTextView);
                helperText.setText("Fetching token from server");
            }
        });

        URL url = null;
        try {
            url = new URL("https://opendata.nationalrail.co.uk/authenticate");
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        }
        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection) url.openConnection();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        http.setDoOutput(true);
        http.setInstanceFollowRedirects(false);
        http.setRequestProperty("Accept","*/*");
        try {
            http.setRequestMethod("POST");
        } catch (ProtocolException protocolException) {
            protocolException.printStackTrace();
        }
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String data = "username=samirichards100@gmail.com&password=cizP4wFajM8ma!m";
        try {
            http.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int status = 0;
        try {
            status = http.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK)  {
                BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                String responseMessage = sb.toString();

                JSONObject jsonResponse = new JSONObject(responseMessage);
                if (jsonResponse.has("error")){
                    http.disconnect();
                    return -1;
                }
                else{
                    serverToken = jsonResponse.get("token").toString();
                    http.disconnect();
                    return 0;
                }
            }
            else  {
                http.disconnect();
                return -2;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            http.disconnect();
            return -3;
        }
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(), "Generating database, please wait", Toast.LENGTH_SHORT).show();
    }
}
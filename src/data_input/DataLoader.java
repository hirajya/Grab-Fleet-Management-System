// package data_input;

// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import model.driver_table;

// import java.io.File;
// import java.io.IOException;
// import java.text.ParseException;

// public class DataLoader {

//     public static void main(String[] args) {
//         try {
//             loadJsonData("path/to/your/json/file.json");
//         } catch (IOException | ParseException e) {
//             e.printStackTrace();
//         }
//     }

//     public static void loadJsonData(String filePath) throws IOException, ParseException {
//         ObjectMapper objectMapper = new ObjectMapper();
//         JsonNode rootNode = objectMapper.readTree(new File(filePath));

//         for (JsonNode driverNode : rootNode) {
//             String licenseNum = driverNode.get("driver_LicenseNum").asText();
//             int cNumber = driverNode.get("driver_CNumber").asInt();
//             String cPersonNum = driverNode.get("driver_CPersonNum").asText();
//             String sex = driverNode.get("driver_Sex").asText();
//             String fName = driverNode.get("driver_FName").asText();
//             String mName = driverNode.get("driver_MName").asText();
//             String lName = driverNode.get("driver_LName").asText();
//             String birthdate = driverNode.get("driver_Birthdate").asText();
//             int houseNum = driverNode.get("driver_HouseNum").asInt();
//             String city = driverNode.get("driver_City").asText();
//             String street = driverNode.get("driver_Street").asText();
//             String block = driverNode.get("driver_Block").asText();
//             String brgy = driverNode.get("driver_Brgy").asText();
//             String plate = driverNode.get("car_Plate").asText();
//             String licenseExpiry = driverNode.get("driver_LicenseExpiry").asText();
//             int adminId = 1;  // Assuming a default admin ID for now
//             int quotaRecordId = 1;  // Assuming a default quota record ID for now

//             try {
//                 driver_table.insert(licenseNum, cNumber, cPersonNum, sex, fName, mName, lName, birthdate,
//                         houseNum, city, street, block, brgy, plate, licenseExpiry, adminId, quotaRecordId);
//             } catch (ParseException e) {
//                 e.printStackTrace();
//             }
//         }
//     }
// }

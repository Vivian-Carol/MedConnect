//package org.me.gcu.medconnect.utils;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
//import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
//import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
//import com.amazonaws.services.dynamodbv2.model.KeyType;
//import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
//import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
//import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
//import com.amazonaws.services.dynamodbv2.model.TableDescription;
//
//import org.me.gcu.medconnect.models.Medication;
//import org.me.gcu.medconnect.models.Pharmacy;
//import org.me.gcu.medconnect.network.AwsClientProvider;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//import java.util.UUID;
//
//public class DataGenerator {
//
//    public static void generateData(Context context) {
//        AwsClientProvider.initialize(context); // Ensure AWS client is initialized
//        AmazonDynamoDB dynamoDBClient = AwsClientProvider.getDynamoDBClient();
//        DynamoDBMapper dynamoDBMapper = AwsClientProvider.getDynamoDBMapper();
//
//        new CreateTableTask(dynamoDBClient, dynamoDBMapper).execute("Pharmacies");
//    }
//
//    private static class CreateTableTask extends AsyncTask<String, Void, Void> {
//        private final AmazonDynamoDB dynamoDBClient;
//        private final DynamoDBMapper dynamoDBMapper;
//
//        CreateTableTask(AmazonDynamoDB dynamoDBClient, DynamoDBMapper dynamoDBMapper) {
//            this.dynamoDBClient = dynamoDBClient;
//            this.dynamoDBMapper = dynamoDBMapper;
//        }
//
//        @Override
//        protected Void doInBackground(String... params) {
//            String tableName = params[0];
//            try {
//                TableDescription tableDescription = dynamoDBClient.describeTable(tableName).getTable();
//                System.out.println("Table already exists. Table description: " + tableDescription);
//            } catch (ResourceNotFoundException e) {
//                createTable(tableName);
//            }
//            insertData();
//            return null;
//        }
//
//        private void createTable(String tableName) {
//            try {
//                CreateTableRequest request = new CreateTableRequest()
//                        .withTableName(tableName)
//                        .withKeySchema(new KeySchemaElement("PharmacyID", KeyType.HASH))
//                        .withAttributeDefinitions(new AttributeDefinition("PharmacyID", ScalarAttributeType.S))
//                        .withProvisionedThroughput(new ProvisionedThroughput(10L, 10L));
//
//                dynamoDBClient.createTable(request);
//                System.out.println("Table created successfully.");
//            } catch (Exception e) {
//                System.err.println("Unable to create table: ");
//                System.err.println(e.getMessage());
//            }
//        }
//
//        private void insertData() {
//            List<String> pharmacyNames = Arrays.asList(
//                    "Saving Grace Pharmacy", "Healing Hands Pharmacy", "Health First Pharmacy", "MediCare Pharmacy",
//                    "PharmaTrust", "CureWell Pharmacy", "LifeCare Pharmacy", "Wellness Pharmacy",
//                    "HealthPlus Pharmacy", "Vitality Pharmacy", "Action Chemist", "African Pharmaceuticals",
//                    "Astrapharm Chemists", "Bestcare Pharmacy", "Biocyte Chemist", "Healthwell Pharmacy",
//                    "Lifebridge Pharma", "Medmart", "Pharmacare", "Pharmacy2U",
//                    "Quintons Pharmacy Limited", "Apple Pharmaceuticals Ltd", "Carecompound", "CareMeds",
//                    "Curemasters", "Customcare Pharmacy", "Dawa Ltd", "Greengrove Pharmacy",
//                    "Healhaven", "Healthcraft", "Medicore", "Medigenius",
//                    "Nanak Chemist", "Sunnycity Pharma");
//
//            List<String> addresses = Arrays.asList(
//                    "Kenyatta Avenue, Nairobi", "Moi Avenue, Mombasa", "Oginga Odinga Street, Kisumu", "Uganda Road, Eldoret",
//                    "Kenyatta Lane, Nakuru", "Tom Mboya Street, Nairobi", "Biashara Street, Nairobi", "Kimathi Street, Nairobi",
//                    "Muindi Mbingu, Nairobi", "Haile Selassie, Nairobi", "Koinange Street, Nairobi", "Mama Ngina Street, Nairobi",
//                    "Ronald Ngala, Nairobi", "Bukani Road, Nairobi", "Hodari Avenue, Nairobi", "Kabras Road, Nairobi",
//                    "Mfangano Street, Nairobi", "Wabera, Nairobi", "Accra Road, Nairobi", "Bhanderi Road, Nairobi",
//                    "Dubois Road, Nairobi", "Harambee Avenue, Nairobi", "Klb Road, Nairobi", "Salim Road, Nairobi",
//                    "Moi Road, Nakuru", "Moi Street, Nakuru", "Moses Mudavadi Road, Nakuru", "Mosque Road, Nakuru",
//                    "Nakuru Kenya Pipeline Company, Nakuru", "Nakuru-Sigor Road, Nakuru", "Pandhit Nehru Road, Nakuru",
//                    "Phillip Njoka Avenue, Nakuru", "Prison Road, Nakuru", "Pyrethrum Estate Four, Nakuru",
//                    "Ronald Ngara Road, Nakuru", "Ronald Ngara Street, Nakuru", "Sagana Road, Nakuru", "Show Ground Road, Nakuru",
//                    "Solai Road, Nakuru", "Stadium Road, Nakuru", "Station Road, Nakuru", "Taita Street, Nakuru",
//                    "Tom Mboya Street, Nakuru", "Turkana Street, Nakuru", "Uhuru Street, Nakuru", "Umardin Road, Nakuru",
//                    "Wareng Street, Nakuru", "Watalii Road, Nakuru", "West Road, Nakuru",
//                    "Dongo Kundu Bypass Highway, Mombasa", "Jomo Kenyatta Avenue, Mombasa", "Makupa Causeway, Mombasa",
//                    "Makupa Roundabout, Mombasa", "Mombasa–Garissa Road, Mombasa", "Mosque Road, Kisumu", "Mosque-Kona Mbuta Road, Kisumu",
//                    "Nairobi Road, Kisumu", "Nehru Road, Kisumu", "New Station Road, Kisumu", "Nyamasaria Fly over, Kisumu",
//                    "Nyerere Road, Kisumu", "Nzoia Road, Kisumu", "Obote Road, Kisumu", "Ochieng Avenue, Kisumu",
//                    "Odera Street, Kisumu", "Odiaga Road, Kisumu", "Ogada Street, Kisumu", "Oginga Odinga Road, Kisumu",
//                    "Oginga Odinga Street, Kisumu", "Ojijo Oteko Road, Kisumu", "Okore Road, Kisumu", "Old Airport Rd., Kisumu",
//                    "Old railway trail, Kisumu", "Omino Cres Road, Kisumu", "Omolo Agar Road, Kisumu", "Ondiek Highway, Kisumu",
//                    "Otieno Oyoo Street, Kisumu", "Pamba Road, Kisumu", "Paul Mbuya Road, Kisumu", "Pramukh Swami Road, Kisumu",
//                    "Ramogi Road, Kisumu", "Riat University Street, Kisumu", "Riddoch Road, Kisumu", "Ring Road, Kisumu",
//                    "Arap Moi Street, Eldoret", "County Assembly Road, Eldoret", "Dharma Road, Eldoret", "Eastern Avenue, Eldoret",
//                    "Elgeyo Road, Eldoret", "Elijah Cheriyot Street, Eldoret", "Farmers Street, Eldoret", "Harambee Road, Eldoret",
//                    "Kago Street, Eldoret", "Kambi Somali, Eldoret", "Kenyatta Street, Eldoret", "Kerio Valley Road, Eldoret",
//                    "Kimalel Street, Eldoret", "Kimathi Avenue, Eldoret", "Kitale - Cherangani Road, Eldoret", "Kitondo Street, Eldoret",
//                    "Makasembo Road, Eldoret", "Market StreetMinor Road, Eldoret", "Mitaa Road, Eldoret", "Moi Street, Eldoret",
//                    "Muliro Street, Eldoret", "Nandi Road, Eldoret", "Oloo Street, Eldoret", "Ronald Ngala Street, Eldoret");
//
//            List<String> phoneNumbers = Arrays.asList(
//                    "+254711000001", "+254711000002", "+254711000003", "+254711000004", "+254711000005");
//
//            List<Medication> commonMedications = Arrays.asList(
//                    new Medication("Paracetamol", "500mg", "50 KES"),
//                    new Medication("Ibuprofen", "400mg", "100 KES"),
//                    new Medication("Aspirin", "300mg", "80 KES"),
//                    new Medication("Cetirizine", "10mg", "150 KES"),
//                    new Medication("Amoxicillin", "250mg", "200 KES")
//            );
//
//            List<Medication> rareMedications = Arrays.asList(
//                    new Medication("Metformin", "500mg", "250 KES"),
//                    new Medication("Loratadine", "10mg", "120 KES"),
//                    new Medication("Azithromycin", "500mg", "300 KES"),
//                    new Medication("Hydroxychloroquine", "200mg", "400 KES"),
//                    new Medication("Doxycycline", "100mg", "350 KES"),
//                    new Medication("Paclitaxel", "100mg", "500 KES"),
//                    new Medication("Docetaxel", "80mg", "450 KES"),
//                    new Medication("Lactulose", "10g/15ml", "200 KES"),
//                    new Medication("Glucosamine/chondrointin", "500mg/400mg", "250 KES"),
//                    new Medication("Venlafaxine", "75mg", "300 KES"),
//                    new Medication("Methotrexate", "2.5mg", "150 KES"),
//                    new Medication("Amitriptyline", "25mg", "100 KES"),
//                    new Medication("Gabapentin", "300mg", "350 KES"),
//                    new Medication("Carbamazepine", "200mg", "200 KES"),
//                    new Medication("Lamotrigine", "100mg", "250 KES"),
//                    new Medication("Levothyroxine", "100mcg", "150 KES"),
//                    new Medication("Sulfasalazine", "500mg", "300 KES"),
//                    new Medication("Mycophenolate mofetil", "500mg", "450 KES"),
//                    new Medication("Tamsulosin", "0.4mg", "200 KES"),
//                    new Medication("Finasteride", "5mg", "250 KES"),
//                    new Medication("Prednisolone", "5mg", "100 KES"),
//                    new Medication("Spironolactone", "25mg", "150 KES"),
//                    new Medication("Hydrochlorothiazide", "25mg", "100 KES"),
//                    new Medication("Losartan", "50mg", "200 KES"),
//                    new Medication("Amlodipine", "5mg", "120 KES"),
//                    new Medication("Olmesartan", "20mg", "300 KES"),
//                    new Medication("Bisoprolol", "5mg", "150 KES"),
//                    new Medication("Rivaroxaban", "20mg", "400 KES"),
//                    new Medication("Apixaban", "5mg", "350 KES"),
//                    new Medication("Dabigatran", "150mg", "450 KES"),
//                    new Medication("Warfarin", "5mg", "100 KES"),
//                    new Medication("Clopidogrel", "75mg", "200 KES"),
//                    new Medication("Atorvastatin", "20mg", "250 KES"),
//                    new Medication("Rosuvastatin", "10mg", "300 KES"),
//                    new Medication("Sitagliptin", "100mg", "400 KES"),
//                    new Medication("Insulin glargine", "100 IU/ml", "500 KES"),
//                    new Medication("Insulin aspart", "100 IU/ml", "450 KES"),
//                    new Medication("Pregabalin", "75mg", "350 KES"),
//                    new Medication("Duloxetine", "30mg", "300 KES"),
//                    new Medication("Bupropion", "150mg", "400 KES"),
//                    new Medication("Escitalopram", "10mg", "200 KES"),
//                    new Medication("Sertraline", "50mg", "200 KES"),
//                    new Medication("Paroxetine", "20mg", "250 KES"),
//                    new Medication("Citalopram", "20mg", "200 KES"),
//                    new Medication("Vortioxetine", "10mg", "350 KES"),
//                    new Medication("Quetiapine", "100mg", "300 KES"),
//                    new Medication("Olanzapine", "10mg", "250 KES"),
//                    new Medication("Risperidone", "2mg", "200 KES"),
//                    new Medication("Aripiprazole", "10mg", "400 KES"),
//                    new Medication("Lurasidone", "40mg", "450 KES"),
//                    new Medication("Ziprasidone", "20mg", "300 KES"),
//                    new Medication("Paliperidone", "3mg", "400 KES"),
//                    new Medication("Lisdexamfetamine", "30mg", "500 KES"),
//                    new Medication("Methylphenidate", "10mg", "300 KES"),
//                    new Medication("Atomoxetine", "25mg", "350 KES"),
//                    new Medication("Modafinil", "100mg", "400 KES"),
//                    new Medication("Naltrexone", "50mg", "250 KES"),
//                    new Medication("Acamprosate", "333mg", "200 KES"),
//                    new Medication("Disulfiram", "500mg", "300 KES")
//            );
//
//            Random rand = new Random();
//
//            for (int i = 0; i < 1000; i++) { // Generate 1000 entries
//                String pharmacyID = UUID.randomUUID().toString();
//                String pharmacyName = pharmacyNames.get(rand.nextInt(pharmacyNames.size()));
//                String address = addresses.get(rand.nextInt(addresses.size()));
//                String phoneNumber = phoneNumbers.get(rand.nextInt(phoneNumbers.size()));
//
//                // Ensure all pharmacies have common medications
//                List<Medication> meds = new ArrayList<>();
//                for (Medication commonMed : commonMedications) {
//                    meds.add(new Medication(commonMed.getMedicationName(), commonMed.getMilligrams(), generateDynamicPrice(commonMed.getPrice())));
//                }
//
//                // Add a random number of rare medications
//                int rareMedCount = rand.nextInt(10) + 1; // Include 1 to 10 rare medications
//                for (int j = 0; j < rareMedCount; j++) {
//                    Medication med = rareMedications.get(rand.nextInt(rareMedications.size()));
//                    meds.add(new Medication(med.getMedicationName(), med.getMilligrams(), generateDynamicPrice(med.getPrice())));
//                }
//
//                String town = address.split(",")[1].trim();
//
//                Pharmacy item = new Pharmacy(pharmacyID, pharmacyName, phoneNumber, address, town, meds);
//
//                dynamoDBMapper.save(item);
//            }
//
//            System.out.println("Data inserted successfully!");
//        }
//
//        private String generateDynamicPrice(String basePrice) {
//            Random rand = new Random();
//            double price = Double.parseDouble(basePrice.replace(" KES", ""));
//            double variation = price * (rand.nextDouble() * 0.4 - 0.2); // +/- 20%
//            return String.format("%.2f KES", price + variation);
//        }
//    }
//}

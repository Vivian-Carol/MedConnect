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
//            List<Medication> medications = Arrays.asList(
//                    new Medication("Paracetamol", "500mg", "50 KES"),
//                    new Medication("Amoxicillin", "250mg", "200 KES"),
//                    new Medication("Ibuprofen", "400mg", "100 KES"),
//                    new Medication("Cetirizine", "10mg", "150 KES"),
//                    new Medication("Aspirin", "300mg", "80 KES"),
//                    new Medication("Metformin", "500mg", "250 KES"),
//                    new Medication("Loratadine", "10mg", "120 KES"),
//                    new Medication("Azithromycin", "500mg", "300 KES"),
//                    new Medication("Hydroxychloroquine", "200mg", "400 KES"),
//                    new Medication("Doxycycline", "100mg", "350 KES"),
//                    new Medication("Nimesulide", "100mg", "150 KES"),
//                    new Medication("Serratiopeptidase", "10mg", "200 KES"),
//                    new Medication("Cefuroxime", "500mg", "250 KES"),
//                    new Medication("Ceftriaxone", "1g", "300 KES"),
//                    new Medication("Cefixime", "200mg", "150 KES"),
//                    new Medication("Ciprofloxacin", "500mg", "100 KES"),
//                    new Medication("Glipizide", "5mg", "50 KES"),
//                    new Medication("Beclomethasone/neomycin", "0.1mg", "200 KES"),
//                    new Medication("Amoxicillin/clavulanate", "875mg/125mg", "300 KES"),
//                    new Medication("Atorvastatin", "10mg", "150 KES"),
//                    new Medication("Gliclazide", "80mg", "100 KES"),
//                    new Medication("Meloxicam", "7.5mg", "120 KES"),
//                    new Medication("Paclitaxel", "100mg", "500 KES"),
//                    new Medication("Docetaxel", "80mg", "450 KES"),
//                    new Medication("Lactulose", "10g/15ml", "200 KES"),
//                    new Medication("Glucosamine/chondrointin", "500mg/400mg", "250 KES"),
//                    new Medication("Venlafaxine", "75mg", "300 KES"),
//                    new Medication("Esomeprazole", "40mg", "150 KES"),
//                    new Medication("Fluphenazine", "25mg", "200 KES"),
//                    new Medication("Oxcarbazepine", "300mg", "250 KES"),
//                    new Medication("Octreotide", "100mcg/ml", "300 KES"),
//                    new Medication("Zolpidem", "10mg", "150 KES"),
//                    new Medication("Lithium Carbonate", "300mg", "100 KES"),
//                    new Medication("Gatifloxacin", "400mg", "200 KES"),
//                    new Medication("Quinine", "300mg", "150 KES"),
//                    new Medication("Ambroxol", "30mg", "100 KES"),
//                    new Medication("Nystatin", "100,000 units/ml", "50 KES"),
//                    new Medication("Fluconazole", "150mg", "100 KES"),
//                    new Medication("Iron/haematinic Preparation", "100mg", "120 KES"),
//                    new Medication("Diclofenac", "50mg", "80 KES"),
//                    new Medication("Carbamazepine", "200mg", "100 KES"),
//                    new Medication("Phenytoin", "100mg", "150 KES"),
//                    new Medication("Acyclovir", "200mg", "200 KES"),
//                    new Medication("Vitamin E", "400 IU", "100 KES"),
//                    new Medication("Griseofulvin", "500mg", "200 KES"),
//                    new Medication("Ofloxacin", "200mg", "150 KES"),
//                    new Medication("Rifampicin", "300mg", "200 KES"),
//                    new Medication("Carbimazole", "5mg", "100 KES"),
//                    new Medication("Chlorhexidine", "0.5%", "50 KES"),
//                    new Medication("Erythromycin", "500mg", "150 KES"),
//                    new Medication("Cyclophosphamide", "50mg", "300 KES"),
//                    new Medication("Bleomycin Sulphate", "15 units", "400 KES"),
//                    new Medication("Vincristine", "1mg", "350 KES"),
//                    new Medication("Vitamin B1/b6/b12", "100mg/200mg/200mcg", "150 KES"),
//                    new Medication("Miconazole", "2%", "100 KES"),
//                    new Medication("Calcium/vitamin D", "500mg/200 IU", "200 KES"),
//                    new Medication("Chlorhexidine/cetrimide", "0.5%/0.5%", "100 KES"),
//                    new Medication("Chloramphenicol", "250mg", "150 KES"),
//                    new Medication("Hyoscine Butylbromide", "10mg", "80 KES"),
//                    new Medication("Omeprazole", "20mg", "100 KES"),
//                    new Medication("Cephalexin", "500mg", "150 KES"),
//                    new Medication("Insulin", "100 IU/ml", "200 KES"),
//                    new Medication("Vitamin/mineral Preparation", "100mg", "50 KES"),
//                    new Medication("Artesunate", "50mg", "100 KES"),
//                    new Medication("Propofol", "10mg/ml", "200 KES"),
//                    new Medication("Lignocaine", "2%", "50 KES"),
//                    new Medication("Heparin", "5000 IU/ml", "150 KES"),
//                    new Medication("Ketamine", "50mg/ml", "300 KES"),
//                    new Medication("Bromhexine/pseudoephedrine", "8mg/60mg", "100 KES"),
//                    new Medication("Methyltestosterone", "10mg", "200 KES"),
//                    new Medication("Human Anti-d Immunoglobulin", "1000 IU", "500 KES"),
//                    new Medication("Cefadroxil", "500mg", "150 KES"),
//                    new Medication("Losartan/hydrochlorthiazide", "50mg/12.5mg", "100 KES"),
//                    new Medication("Sertraline", "50mg", "200 KES"),
//                    new Medication("Nicorandil", "10mg", "150 KES"),
//                    new Medication("Mosapride", "5mg", "100 KES"),
//                    new Medication("Leflunomide", "10mg", "250 KES"),
//                    new Medication("Iron Hydroxide/folic Acid", "100mg/0.5mg", "50 KES"),
//                    new Medication("Halothane", "250ml", "200 KES"),
//                    new Medication("Dihydroartemisinin", "40mg", "100 KES"),
//                    new Medication("Tramadol", "50mg", "100 KES"),
//                    new Medication("Valganciclovir", "450mg", "300 KES"),
//                    new Medication("Nystatin/neomycin/gramicidin/triamcinolon", "100,000 units/2.5mg/0.25mg", "200 KES"),
//                    new Medication("Tribenoside/lignocaine", "5mg/20mg", "100 KES"),
//                    new Medication("Tetrahydrozoline", "0.05%", "50 KES"),
//                    new Medication("Mebeverine", "135mg", "150 KES"),
//                    new Medication("Betamethasone", "0.5mg", "100 KES"),
//                    new Medication("Amodiaquine", "200mg", "150 KES"),
//                    new Medication("Framycetin/gramicidin/dexamethasone", "0.5%/0.05%/0.05%", "200 KES"),
//                    new Medication("Tamsulosin", "0.4mg", "150 KES"),
//                    new Medication("Ciprofloxacin/dexamethasone", "0.3%/0.1%", "200 KES"),
//                    new Medication("Amoxiciillin", "500mg", "100 KES"),
//                    new Medication("Gentamicin", "40mg/ml", "100 KES"),
//                    new Medication("Ethinylestradiol/levonorgestrel", "30mcg/150mcg", "100 KES"),
//                    new Medication("Tamoxifen", "10mg", "200 KES"),
//                    new Medication("Liniment/balm", "100ml", "50 KES"),
//                    new Medication("Vit B Complex", "100mg", "50 KES"),
//                    new Medication("Lomefloxacin", "400mg", "200 KES"),
//                    new Medication("Mometasone", "0.1%", "100 KES"),
//                    new Medication("Vitamin E/aloe Vera", "400 IU/100mg", "150 KES"),
//                    new Medication("Budesonide", "0.5mg/2ml", "200 KES"),
//                    new Medication("Clopidogrel", "75mg", "100 KES"),
//                    new Medication("Nifedipine", "10mg", "150 KES"),
//                    new Medication("Lansoprazole/clarithromycin/tinidazole", "30mg/500mg/500mg", "300 KES"),
//                    new Medication("Benzathine Penicillin", "1.2 million units", "200 KES"),
//                    new Medication("Procaine Penicillin", "600,000 units", "100 KES"),
//                    new Medication("Iron/haematinic Preparation", "100mg", "50 KES"),
//                    new Medication("Paracetamol/ibuprofen", "500mg/200mg", "50 KES"),
//                    new Medication("Ampicillin/cloxacillin", "250mg/250mg", "200 KES"),
//                    new Medication("Lindane", "1%", "100 KES"),
//                    new Medication("Adapalene", "0.1%", "100 KES"),
//                    new Medication("Valganciclovir", "450mg", "200 KES"),
//                    new Medication("Nystatin/neomycin/gramicidin/triamcinolon", "100,000 units/2.5mg/0.25mg", "100 KES"),
//                    new Medication("Tribenoside/lignocaine", "5mg/20mg", "50 KES"),
//                    new Medication("Ciprofloxacin/dexamethasone", "0.3%/0.1%", "200 KES"),
//                    new Medication("Amoxiciillin", "500mg", "100 KES"),
//                    new Medication("Lignocaine/adrenaline", "2%/1:100,000", "100 KES"),
//                    new Medication("Heparin/allantoin", "50mg/10mg", "50 KES"),
//                    new Medication("Fluconazole", "150mg", "200 KES"),
//                    new Medication("Ibuprofen", "400mg", "100 KES")
//            );
//
//            Random rand = new Random();
//
//            for (int i = 0; i < 1000; i++) { // Generate 1000 entries
//                String pharmacyID = UUID.randomUUID().toString();
//                String pharmacyName = pharmacyNames.get(rand.nextInt(pharmacyNames.size()));
//                String address = addresses.get(rand.nextInt(addresses.size()));
//                String phoneNumber = phoneNumbers.get(rand.nextInt(phoneNumbers.size()));
//                // Ensure at least 20 medications for each pharmacy
//                List<Medication> meds = new ArrayList<>(medications.subList(0, 20 + rand.nextInt(medications.size() - 20)));
//                String town = address.split(",")[1].trim();
//
//                Pharmacy item = new Pharmacy(pharmacyID, pharmacyName, phoneNumber, address, town, meds);
//
//                dynamoDBMapper.save(item);
//            }
//
//            System.out.println("Data inserted successfully!");
//        }
//    }
//}

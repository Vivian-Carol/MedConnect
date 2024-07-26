package org.me.gcu.medconnect.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;

import org.me.gcu.medconnect.network.AwsClientProvider;

public class TableCreator {

    private static final String TAG = "TableCreator";

    public static void createTables() {
        new CreateTablesTask().execute();
    }

    private static class CreateTablesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            AmazonDynamoDB dynamoDBClient = AwsClientProvider.getDynamoDBClient();

            // Create Users Table
            createTableIfNotExists(dynamoDBClient, "Users",
                    new AttributeDefinition("userId", "S"),
                    new KeySchemaElement("userId", KeyType.HASH)
            );

            // Create Prescriptions Table
            createTableIfNotExists(dynamoDBClient, "Prescriptions",
                    new AttributeDefinition("id", "S"),
                    new KeySchemaElement("id", KeyType.HASH)
            );

            return null;
        }

        private void createTableIfNotExists(AmazonDynamoDB dynamoDBClient, String tableName,
                                            AttributeDefinition attributeDefinition,
                                            KeySchemaElement keySchemaElement) {
            try {
                TableDescription tableDescription = dynamoDBClient.describeTable(new DescribeTableRequest().withTableName(tableName)).getTable();
                if (tableDescription != null && tableDescription.getTableStatus().equals(TableStatus.ACTIVE.toString())) {
                    Log.d(TAG, tableName + " table already exists and is active.");
                    return;
                }
            } catch (Exception e) {
                Log.d(TAG, tableName + " table does not exist, creating...");
            }

            CreateTableRequest createTableRequest = new CreateTableRequest()
                    .withTableName(tableName)
                    .withKeySchema(keySchemaElement)
                    .withAttributeDefinitions(attributeDefinition)
                    .withProvisionedThroughput(new ProvisionedThroughput(5L, 5L));

            try {
                dynamoDBClient.createTable(createTableRequest);
                Log.d(TAG, tableName + " table created successfully");
            } catch (Exception e) {
                Log.e(TAG, "Error creating " + tableName + " table: " + e.getMessage());
            }
        }
    }
}


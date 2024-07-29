package org.me.gcu.medconnect.network;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClient;

public class AwsClientProvider {

    private static AmazonS3 s3Client;
    private static AmazonDynamoDB dynamoDBClient;
    private static DynamoDBMapper dynamoDBMapper;
    private static AmazonTextract textractClient;
    private static CognitoCachingCredentialsProvider credentialsProvider;

    private static final String REGION = "us-east-1";
    private static final String COGNITO_POOL_ID = "us-east-1:82924dbc-f1a3-41a5-9c3b-f7e8a3ada9fd";

    public static void initialize(Context context) {
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                COGNITO_POOL_ID,
                Regions.US_EAST_1
        );

        s3Client = new AmazonS3Client(credentialsProvider);
        s3Client.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_EAST_1));

        dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);
        dynamoDBClient.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_EAST_1));

        dynamoDBMapper = new DynamoDBMapper(dynamoDBClient);

        textractClient = new AmazonTextractClient(credentialsProvider);
        textractClient.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_EAST_1));
    }

    public static AmazonS3 getS3Client() {
        return s3Client;
    }

    public static AmazonDynamoDB getDynamoDBClient() {
        return dynamoDBClient;
    }

    public static DynamoDBMapper getDynamoDBMapper() {
        return dynamoDBMapper;
    }

    public static AmazonTextract getTextractClient() {
        return textractClient;
    }

    public static TransferUtility getTransferUtility(Context context) {
        return TransferUtility.builder()
                .context(context.getApplicationContext())
                .s3Client(getS3Client())
                .build();
    }
}

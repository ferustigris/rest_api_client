package ferus.tigris;

import ferus.tigris.client.Responsable;
import ferus.tigris.client.RestApiClient;
import ferus.tigris.client.RestReader;
import ferus.tigris.queries.*;
import ferus.tigris.repos.ApisRepository;

public class Client {
    private static final ApisRepository apisRepo = new ApisRepository();
    private static final QueryFactory queryFactory = new QueryFactory();

    public static void main(String[] args) {
        if (args.length == 0) {
            // list of available APIs
            System.out.println(apisRepo);
            System.exit(0);
        } else if ("--help".equals(args[0])) {
            System.out.println("Usage: java ferus.tigris.Client [--api=api-name] [--query=query-params...]");
            System.exit(1);
        }

        BaseQuery query = null;
        // find out which API to instantiate
        if (args[0].startsWith("--api=")) {
            String apiName = args[0].substring(6);
            query = (BaseQuery) queryFactory.createQuery(apiName);
            if (query == null) {
                System.out.println("Not defined API: " + apiName);
                System.exit(1);
            }
            System.out.println("API to query: " + query.getRequestUrl());
        } else {
            System.out.println("Set up API name in --api parameter.");
            System.exit(1);
        }

        if (args.length == 1) {
            // list of queries available for the chosen API
            System.out.println("Available endpoints:\n\t" +
                    String.join(", \t", query.endpoints()));
            System.exit(0);
        }

        // instantiate the chosen query and execute it
        RestApiClient client = new RestApiClient(new RestReader());
        Responsable response = client.run(query);
        // presenting the results
        System.out.println(response.toString());
    }
}

package pythonService;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

/**
 * @autor zhenrenwu
 * @date 2022/6/13 2:19 上午
 */
public class TestClient {
    private final ManagedChannel channel;
    private final EditDistServiceGrpc.EditDistServiceBlockingStub blockingStub;

    public TestClient(String host, int port){
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = EditDistServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException{
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    public void PythonServer(String query, String target){
        EditDist.EditDistRequest request = EditDist.EditDistRequest.newBuilder().setQuery(query).setTarget(target).build();
        EditDist.EditDistResponse response;
        try {
            response = blockingStub.editDist(request);
        } catch (Exception e) {
            System.out.println("RPC请求失败{0}" + e.getMessage());
            e.printStackTrace();
            return;
        }
        System.out.println("服务结果： ");
        System.out.println(response.getResult());
    }

    public static void main(String[] args) {
        TestClient testClient = new TestClient("localhost", 50000);
        testClient.PythonServer("长津湖", "长津湖之水门桥");
    }
}

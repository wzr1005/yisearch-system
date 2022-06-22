package pythonService;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @autor zhenrenwu
 * @date 2022/6/13 2:19 上午
 */
@Component
public class PythonServiceClient {
    private final ManagedChannel channel;
    private final EditDistServiceGrpc.EditDistServiceBlockingStub blockingStub;


    public PythonServiceClient(String host, int port){
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = EditDistServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException{
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    public float EditDistServer(String query, String target){
        EditDist.EditDistRequest request = EditDist.EditDistRequest.newBuilder().setQuery(query).setTarget(target).build();
        EditDist.EditDistResponse response;
        try {
            response = blockingStub.editDist(request);
        } catch (Exception e) {
            System.out.println("RPC请求失败{0}" + e.getMessage());
            e.printStackTrace();
            return 0.0F;
        }
        System.out.println(String.format("服务结果：query=%s\ttarget=%s相似度为 ", query, target));
        System.out.println(response.getResult());
        return response.getResult();
    }

    public static void main(String[] args) {
        PythonServiceClient pythonServiceClient = new PythonServiceClient("localhost", 50000);
        pythonServiceClient.EditDistServer("短津湖", "长津湖");
    }
}

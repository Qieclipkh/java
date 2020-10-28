import com.thunisoft.cloud.storage.client.base.protocol.attribtues.AttributeImpl;
import com.thunisoft.cloud.storage.client.base.protocol.attribtues.IAttributes;
import com.thunisoft.cloud.storage.client.minio.protocol.AttributesProtocol;
import com.thunisoft.cloud.storage.client.minio.protocol.ProtocolAnalysis;
import org.junit.Test;

public class TestPo {
    @Test
    public void testP(){
        AttributesProtocol p = new AttributesProtocol();
        p.setStorageId("dad");
        p.setArchiveId("dasdad");
        AttributeImpl attr = new AttributeImpl();
        attr.setFileName("11.DOCX");
        attr.setFilePrePath("/1/1/22/");
        p.setAttributes(attr);
        System.out.println(p.toPlainString());
    }
}

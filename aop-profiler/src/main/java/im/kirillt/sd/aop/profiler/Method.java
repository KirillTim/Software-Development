package im.kirillt.sd.aop.profiler;

public class Method {
    public final String signature;
    Method(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return signature;
    }
}


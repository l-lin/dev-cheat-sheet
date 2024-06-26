package lin.louis.logging_with_aop.logging;

import org.slf4j.MDC;

class MdcHandler {

    void put(MdcInput mdcInput) {
        MDC.put(mdcInput.key(), mdcInput.value());
    }

    void remove(MdcInput mdcInput) {
        MDC.remove(mdcInput.key());
    }

    boolean isMdcAlreadySet(MdcInput mdcInput) {
        return MDC.get(mdcInput.key()) != null;
    }
}

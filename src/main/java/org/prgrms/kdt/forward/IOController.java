package org.prgrms.kdt.forward;

import org.prgrms.kdt.controller.VoucherController;
import org.prgrms.kdt.controller.request.CreateVoucherRequest;
import org.prgrms.kdt.forward.io.ConsoleInput;
import org.prgrms.kdt.forward.io.ConsoleOutput;
import org.prgrms.kdt.forward.io.Input;
import org.prgrms.kdt.forward.io.Output;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class IOController {
    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final VoucherController voucherController;

    public IOController(ConsoleInput input, ConsoleOutput output, VoucherController voucherController) {
        this.input = input;
        this.output = output;
        this.voucherController = voucherController;
    }

    public void run() {
        boolean exit = false;
        String introduction = "=== Voucher Program === \nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.";
        output.write(introduction);

        do {
            try {
                String command = input.readLine();
                output.write("> 선택 : " + command);

                switch (command) {
                    case "create" -> {
                        output.write(voucherController.requestVoucherInfo());
                        String requestedVoucherInfo = input.readLine();
                        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(requestedVoucherInfo);
                        output.write(voucherController.create(createVoucherRequest));
                    }
                    case "list" -> {
                        output.write(voucherController.list());
                    }
                    case "exit"-> {
                        output.write(voucherController.exit());
                        exit = true;
                    }
                }
            } catch (RuntimeException e) {
                output.write("> " + e.getMessage());
            }

            if (!exit) output.write(introduction);
        } while (!exit);

        input.close();
    }
}

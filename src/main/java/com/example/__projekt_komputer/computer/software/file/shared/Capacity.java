package com.example.__projekt_komputer.computer.software.file.shared;

public enum Capacity {
    GB1(1_073_741_824L),  // 1 GB w bajtach
    GB2(2_147_483_648L),  // 2 GB w bajtach
    GB4(4_294_967_296L),  // 4 GB w bajtach
    GB8(8_589_934_592L),  // 8 GB w bajtach
    GB16(17_179_869_184L), // 16 GB w bajtach
    GB32(34_359_738_368L), // 32 GB w bajtach
    GB64(68_719_476_736L); // 64 GB w bajtach

    private final Long size;

    Capacity(Long size) {
        this.size = size;
    }

    public Long getSize() {
        return size;
    }
}

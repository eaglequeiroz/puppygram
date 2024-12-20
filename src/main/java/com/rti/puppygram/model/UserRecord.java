package com.rti.puppygram.model;

import java.util.List;

public record UserRecord(String name, String email, String password, List<Role> roles) {
}

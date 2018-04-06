package com.madless.erasmusapp;

class StudentCheck {
    private int id;
    private boolean checked;

    public StudentCheck() {
    }

    public StudentCheck(int id, boolean checked) {
        this.id = id;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

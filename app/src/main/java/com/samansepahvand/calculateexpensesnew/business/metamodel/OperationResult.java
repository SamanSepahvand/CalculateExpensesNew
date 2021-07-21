package com.samansepahvand.calculateexpensesnew.business.metamodel;

import com.google.gson.annotations.Expose;

import java.util.List;


public class OperationResult<T> {
    @Expose
    public String Message;
    @Expose
    public boolean IsSuccess;
    @Expose
    public String Exception;
    @Expose
    public T Item;
    @Expose
    public List<T> Items;
    @Expose
    public int TotalItems;

    public OperationResult() {
    }

    public OperationResult(String message, boolean isSuccess, String exception) {
        Message = message;
        IsSuccess = isSuccess;
        Exception = exception;
    }

    public OperationResult(String message, boolean isSuccess, String exception, T item, List<T> items) {
        Message = message;
        IsSuccess = isSuccess;
        Exception = exception;
        Item = item;
        Items = items;
    }
}

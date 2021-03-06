package com.karlgrund.expense.tracker.dao;

import com.karlgrund.expense.tracker.dto.Purchase;
import com.karlgrund.expense.tracker.mapper.PurchaseMapper;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(PurchaseMapper.class)
public abstract class PurchaseDAO {

    @SqlQuery("SELECT * FROM purchase WHERE "
        + "purchase_date = :purchaseDate "
        + "AND price = :price "
        + "AND currency = :currencyId")
    public abstract Purchase purchaseExists(@BindBean Purchase purchase);

    @SqlUpdate("INSERT INTO "
        + "purchase(uuid, event_name, price, currency, purchase_date) "
        + "VALUES( "
        + ":purchaseUUID, "
        + ":eventName, "
        + ":price, "
        + ":currencyId, "
        + ":purchaseDate)")
    public abstract void store(@BindBean Purchase purchase);

    @SqlQuery("SELECT * FROM purchase "
        + "WHERE event_name = :eventName")
    public abstract List<Purchase> getPurchasesFor(@Bind("eventName") String eventName);

    @SqlQuery("SELECT * FROM purchase "
        + "WHERE uuid = :uuid")
    public abstract Purchase getPurchase(@Bind("uuid") String purchaseUUID);

    @SqlQuery("SELECT p.uuid, "
        + "p.event_name,"
        + "p.price,"
        + "p.currency,"
        + "p.purchase_date "
        + "FROM purchase p "
        + "INNER JOIN partial_payments pp "
        + "ON p.uuid = pp.purchase_uuid "
        + "WHERE event_name = :eventName "
        + "AND pp.email = :email")
    public abstract List<Purchase> getPurchase(@Bind("eventName") String eventName, @Bind("email") String email);

    @SqlUpdate("DELETE FROM purchase where uuid = :purchaseUUID")
    public abstract void remove(@Bind("purchaseUUID") String purchaseUUID);
}

package com.chargnn.utils.file;

import com.chargnn.Main;
import com.chargnn.api.NameFetcher;
import com.chargnn.service.EconomyService;
import net.milkbowl.vault.economy.Economy;

import java.io.IOException;
import java.util.UUID;

public class BalanceFileManager extends AbstractYml {

    private Economy econ = new EconomyService();

    public BalanceFileManager(Main main, String fileName) throws IOException {
        super(main, fileName);
    }

    public void saveBalances() throws IOException {
        for(UUID uuid : EconomyService.balances.keySet()){
            fileConfiguration.set("balances." + uuid, EconomyService.balances.get(uuid));
        }

        this.save();
    }

    public void loadBalances(){
        if(!this.file.exists()){
            this.file.getParentFile().mkdirs();
            main.saveResource(this.fileName, false);
        }

        if(!fileConfiguration.contains("balances")) return;

        for(String uuid : fileConfiguration.getConfigurationSection("balances").getKeys(false)){
            econ.depositPlayer(NameFetcher.getName(uuid), fileConfiguration.getDouble("balances." + uuid));
        }
    }
}

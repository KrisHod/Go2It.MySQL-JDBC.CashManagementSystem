package service;

import entity.Payment;
import util.MerchantRepository;

public class MerchantService {
    MerchantRepository merchantRepository = new MerchantRepository();

    public boolean getAll() {
        return merchantRepository.getAll().isEmpty();
    }

}

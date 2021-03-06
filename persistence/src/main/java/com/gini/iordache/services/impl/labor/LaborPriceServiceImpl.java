package com.gini.iordache.services.impl.labor;

import com.gini.errors.labor.LaborPriceException;
import com.gini.errors.labor.InvalidPriceException;
import com.gini.iordache.dao.iterfaces.LaborPriceDao;
import com.gini.iordache.entity.labor.LaborPrice;
import com.gini.iordache.services.interfaces.LaborPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Service
public class LaborPriceServiceImpl implements LaborPriceService {


    private final LaborPriceDao laborPriceDao;
    private Optional<LaborPrice> optLaborPrice;


    @Autowired
    public LaborPriceServiceImpl(LaborPriceDao laborPriceDao) {
        this.laborPriceDao = laborPriceDao;
    }



    //am nevoie de metoda aceasta deoarece metoda findLaborPrices() da eroare cand folosesc @PostConstruct
    // zice ca nu vede tranzactia deoarece contextul nu este inca incarcat => dupa ce se incarca
    //contextul se apeleaza si findLaborPrices()

    //https://www.devpragmatic.com/2017/01/spring-transakcje-init-method.html
    //https://stackoverflow.com/questions/17346679/transactional-on-postconstruct-method
    @EventListener(ContextRefreshedEvent.class)
    public void appContextLoaded(ContextRefreshedEvent evt){
        evt.getApplicationContext()
                .getBean(LaborPriceServiceImpl.class)
                                        .findLaborPrices();
    }



    @Override
    @Transactional
    public void findLaborPrices(){
        optLaborPrice = laborPriceDao.findAllLaborPrices();
    }



    @Override
    public LaborPrice findAllPrices(){

        return optLaborPrice.orElseGet(LaborPrice::new);

    }



    @Override
    @Transactional
    public void createAllLaborPrices(LaborPrice laborPrice){



        if(optLaborPrice.isEmpty()){
            laborPriceDao.createAllLaborPrices(laborPrice);
            findLaborPrices();
            return;
        }

        throw new RuntimeException("Prices are already created!");

    }


    @Override
    @Transactional
    public void updatePrices(double newPrice, String categoryPrice){

        if(newPrice < 0){
            throw new InvalidPriceException("negative price");
        }


        if(optLaborPrice.isPresent()) {
            var id = optLaborPrice.get().getId();


            switch (categoryPrice) {


                case  "MECHANICAL" :
                        laborPriceDao.updateMechanicalLaborPrice(newPrice, id);
                        break;

                case "BODY":
                        laborPriceDao.updateBodyLaborPrice(newPrice, id);
                        break;
                case "ELECTRIC":
                        laborPriceDao.updateElectricalLaborPrice(newPrice, id);
                        break;

                case "NORMAL":
                        laborPriceDao.updateNormalLaborPrice(newPrice, id);
                        break;

                case "ITP_DIESEL":
                        laborPriceDao.updateItpDieselEnginePrice(newPrice, id);
                        break;


                case "ITP_GASOLINE":
                        laborPriceDao.updateItpGasolineEnginePrice(newPrice, id);
                        break;

                case "ITP_SUV":
                        laborPriceDao.updateItpSuvPrice(newPrice, id);
                        break;

                case "ITP_TRUCK":
                        laborPriceDao.updateItpTruckPrice(newPrice, id);
                        break;

                default:
                        throw new RuntimeException("Price category invalid");
            }
            findLaborPrices();
            return;
        }
        throw new LaborPriceException("To update the the prices you have to create them first!");
    }


    public Optional<LaborPrice> getOptLaborPrice() {
        return optLaborPrice;
    }
}

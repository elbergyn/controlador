package net.inforgyn.dataModel;

import java.util.List;

import javax.faces.model.ListDataModel;

import net.inforgyn.model.DespesaMensal;

import org.primefaces.model.SelectableDataModel;

public class DespesaMensalDataModel extends ListDataModel<DespesaMensal> implements SelectableDataModel<DespesaMensal>{

	public DespesaMensalDataModel(List<DespesaMensal> list) {
		super(list);
	}

	@Override
	public DespesaMensal getRowData(String rowKey) {
		/*List<DespesaMensal> despesas = (List<DespesaMensal>) getWrappedData();

        for(DespesaMensal despesa : despesas) {
            if(despesa.getDebito().toString().equals(rowKey))
                return despesa;
        }*/
		DespesaMensal despesa = (DespesaMensal) ((SelectableDataModel) getWrappedData()).getRowData(rowKey);
		System.out.println(despesa.getId());
        return despesa;
	}

	@Override
	public Object getRowKey(DespesaMensal despesa) {
		return despesa.getId();
	}

}

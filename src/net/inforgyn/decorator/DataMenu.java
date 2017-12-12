package net.inforgyn.decorator;

import java.util.Date;

import net.inforgyn.util.DataUtil;

public class DataMenu extends Date {
	private static final long serialVersionUID = 1L;
	private Date date;

	public DataMenu() {
		super();
	}

	public DataMenu(Date date) {
		this.date = date;
	}

	public DataMenu(long arg0) {
		super(arg0);
		date = new Date(arg0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		DataMenu dm = (DataMenu)obj;
		if (this == dm)
			return true;
		if(dm.toString().equals(this.toString()))
			return true;

		return false;
	}

	@Override
	public String toString() {
		return DataUtil.getDataPadraoString(this.date);
	}
}
package generic_filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionFilters {
	
	public static <T> void removeDuplicates(List<T> list){
		Set<T> set = new LinkedHashSet<T>(list);
		list.clear();
		list.addAll(set);
	}
	
	public static <T> Map<T, Integer> countDuplicates(List<T> list){
		Map<T, Integer> map = new HashMap<T, Integer>();
		
		for(T ele : list){
			int counter = 1;
			if(map.containsKey(ele))
			{
				counter = map.get(ele) + 1;
			}
			map.put(ele, counter);
		}
		
		for(Iterator<T> it = map.keySet().iterator(); it.hasNext(); ){
		   T key = it.next();
		   if(map.get(key) == 1)
		   {
			   it.remove();
		   }
		}
		
		return map;
	}

}

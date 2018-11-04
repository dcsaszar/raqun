package de.huberlin.informatik.nmodelcompare.visualization;

import org.ujmp.core.Matrix;
import org.ujmp.core.genericmatrix.impl.DefaultDenseGenericMatrix2D;

import de.huberlin.informatik.nmodelcompare.*;

public class VisualizeSimilarityMatrix
{
	public static void main(String... args)
	{
		NModelWorld world = NModelWorldLoader.loadEcore(args);
		if (world.getNodes().size() == 0) {
			System.out.println("Using fallback dataset.");
			world = NModelWorldLoader.loadEcore("testdata/react_todo_app_2017021115_Akasky70_react_todo_app_step_10_c15f550b.ecore",
					"testdata/react_todo_app_2017021519_Akasky70_react_todo_app_step_15_4fe6b982.ecore",
					"testdata/react_todo_app_2017062714_master_c9ef612a.ecore");
		}
		Similarities similarities = world.findSimilarities(5);
		Matrix matrix = new DefaultDenseGenericMatrix2D<>(similarities.get2DWidth(), similarities.get2DWidth());
		matrix.setLabel("");
		for (int i = 0; i < similarities.get2DWidth(); i++) {
			Node nodeI = world.getNodes().get(i);
			matrix.setRowLabel(i, nodeI.getDescription());
			matrix.setColumnLabel(i, nodeI.getDescription());
			for (int j = 0; j < similarities.get2DWidth(); j++) {
				Node nodeJ = world.getNodes().get(j);
				matrix.setAsString("", i, j);
				if (similarities.getDistance(i, j) != null) {
					Double d = similarities.getNormalizedDistance(i, j);
					if (d == 0) {
						matrix.setAsString(nodeI.getFullName(), i, j);
					}
					else {
						matrix.setAsDouble(((1 - d) * (nodeI.isInSameModel(nodeJ) ? -1 : 1)), i, j);
					}
				}
			}
		}
		matrix.showGUI();
	}
}

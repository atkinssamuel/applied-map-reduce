# MapReduce Applied
## Project Description
The purpose of this project is to gain a thorough understanding of Hadoop MapReduce through application. MapReduce is applied in two different contexts. The first is a kMeans clustering algorithm on a large dataset of 2D points. The second is a line counting program that performs on a large set of text data. Through these applications I was able to become proficient in MapReduce and Java. 

## k-Means Clustering
### k-Means Clustering Implementation
k-Means clustering with MapReduce was implemented in the ```kmeans``` directory. ```k=4``` and ```k=8``` were used, and the results have been included in ```kmeans/results/results.txt```. The CLI was not used because of my familiarity with IntelliJ. To run the MapReduce programs, I created a Java Console Application and modified the run configuration with my command line arguments. I have included the full output of running my k-Means MapReduce program in the ```kmeans/results/console_output.txt``` file. The jar files for both the k-Means algorithm and the line counter are present in the ```jars``` folder. 

### Advantages & Disadvantages of k-Means with MapReduce
The advantages and disadvantages of using MapReduce in the context of k-Means clustering are as follows:  

**Advantages**: 
- Speed
    - MapReduce allows computations to be executed in parallel. Unused computing resources as a result of the constraint of sequential operations can now be utilized. In the context of k-Means, the distance metrics of each point with respect to the centroids can be computed in parallel which results in a huge speed up.
- Scalability
    - As the dataset grows, the demand for parallelization grows. MapReduce scales much better than standard loop-based calculations because of the ability to harness all available computing power. Given that our dataset is quite large, the scalability of MapReduce is apparent. 
- Control
    - MapReduce allows us to control exactly how we harness the computing power. If we decide to change our algorithm, we can optimize our computing power accordingly to maintain reasonable performance. Suppose we wanted to modify our existing algorithm to include canopy pre-clustering. We would easily be able to specify exactly how we would like our computing resources to be utilized. 

**Disadvantages**:
- Complexity
    - For some, using MapReduce to implement k-Means may not be intuitive. In the context of code understanding, MapReduce adds a layer of complexity to k-Means. If we were to implement this algorithm using SkLearn, for example, it would be a one-line intuitive solution.
- Flexibility
    - MapReduce constrains us to thinking in terms of a Mapper and a Reducer. Some applications are difficult to formulate in this way. Given the simplicity of our algorithm, we were able to formulate a solution using a Mapper and a Reducer. A more complex algorithm that might build on top of k-Means could potentially prove difficult to formulate using a Mapper and a Reducer.


## Canopy Selection for k-Means Clustering
### Reducing the Number of Distance Comparisons Using Canopy Selection
The canopy selection algorithm allows one to dramatically reduce the number of comparisons used in a clustering algorithm. The algorithm classifies the points into canopies using two distance metrics. The first distance metric is a loose distance metric that is used to add points to a given canopy. This distance metric should be extremely fast. The second distance metric is a tight metric that is used to restrict points from being added to other canopies. This distance metric should be more accurate. After the points are initially clustered to into canopies, they can be further clustered using a more accurate and less-efficient algorithm like k-Means. 

Without canopy clustering, we must compare all N data points with all k clusters resulting in kN comparisons each centroid update. With canopy clustering, we only need to compare the points in overlapping canopies. Each canopy contains fn/c points where f is the amount in which the canopies overlap, n is the number of data points, and c is the number of canopies. Each cluster needs to compare the points in its own canopy with the points in the overlapping canopies. For all clusters, this results in nkf^2/c comparisons per operation which, for f close to 1, is a 1/c speed up. 

With respect to distance metrics in the context of k-Means clustering, we could use the Manhattan distance as the loose distance metric threshold. This would result in a rapid comparison which is desired. We could alternatively use the Euclidean distance which would be slower but more accurate. For the tight distance metric, we desire more accuracy, so the Euclidean distance should be used. 

### Canopy Selection on MapReduce
Using canopy selection with MapReduce offers further performance enhancement. During every map the mapper will determine if each data point is within the distance threshold of any already specified canopy centers. If the point is within this threshold then it is discarded. Otherwise, this point will be added to a list of canopy center candidates. The reducer receives the canopy centers. It removes the canopies that are within the same threshold (i.e. duplicate canopy candidates for the same canopy center). These distance metrics are then used to determine which points belong to which candidates. Remember that points can belong to multiple canopies. 

### Canopy Selection and k-Means Clustering
Canopy pre-clustering and k-Means can be used in conjunction with MapReduce to rapidly speed up the clustering process. First, the MapReduce canopy pre-clustering method detailed above is applied. This results in a series of points belonging to various canopies. 

Then, the k-Means algorithm implemented in this project can be applied to each of the defined canopies. Each cluster needs to compare the points in its own canopy with the points in the overlapping canopies. Thus, the k-Means driver code present in the Main class must be modified to accommodate this key difference. The k-Means algorithm will then iterate until convergence and given appropriate distance thresholds, the algorithm will converge to an optimal solution much faster than a vanilla k-Means implementation. 

## Line Counter
A line-counter was also implemented in ```shakespeare-line-count/```. This MapReduce program counts all the lines that are present in ```shakespeare.txt```. The results are included in ```shakespeare-line-count/results/results.txt```. The CLI was not used because of my familiarity with IntelliJ. To run the MapReduce programs, I created a Java Console Application and modified the run configuration with my command line arguments. I have included the full output of running my k-Means MapReduce program in the ```shakespeare-line-count/results/console_output.txt``` file. 
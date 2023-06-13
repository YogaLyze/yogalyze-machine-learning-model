# YogaLyze Machine Learning Documentation

Following details on how to replicate our steps.
## Scraping Data
1. Open [Data](https://github.com/YogaLyze/yogalyze-machine-learning-model/tree/main/Data) Folder.
2. Download the [Yoga-82 Dataset](https://github.com/YogaLyze/yogalyze-machine-learning-model/blob/main/Data/Yoga-82.zip) and [Scaping_Data.ipynb](https://github.com/YogaLyze/yogalyze-machine-learning-model/blob/main/Data/Scraping_Data.ipynb) file for scraping data.
3. Put the .ipynb file and dataset in same folder and run the .ipynb file from start to finish.
4. The Data will be downloaded.

## Train Model 
1. Open [Model](https://github.com/YogaLyze/yogalyze-machine-learning-model/tree/main/Model) Folder and download the [Model.ipynb](https://github.com/YogaLyze/yogalyze-machine-learning-model/blob/main/Model/Model.ipynb) file.
2. Put data that obtained in scraping data process to same folder with .ipynb file.
3. Run .ipynb file from start to finish.
4. .h5 model will be downloaded.

## Convert Model to TfLite
1. Open [Convert TfLite](https://github.com/YogaLyze/yogalyze-machine-learning-model/tree/main/Convert%20TfLite) folder and download [Convert.ipynb](https://github.com/YogaLyze/yogalyze-machine-learning-model/blob/main/Convert%20TfLite/Convert.ipynb) file.
2. Put .h5 model that obtained in train model to same folder with .ipynb file.
3. Run .ipynb file from start to finish.
4. .tflite model will be downloaded and ready to deploy.

## Additional
- You can just download the .h5 model in this [Google Drive Link](https://drive.google.com/drive/u/0/folders/1l-wpZLUWI-v3GK0U1uWXQcDZGrA6Ug7l) and choose what model you want to download and just convert it to TfLIte
- You can just download the tflite model in [Convert TfLite](https://github.com/YogaLyze/yogalyze-machine-learning-model/tree/main/Convert%20TfLite) folder and download [Convert.ipynb](https://github.com/YogaLyze/yogalyze-machine-learning-model/blob/main/Convert%20TfLite/Convert.ipynb) Folder for Back Pain, Anxiety, Flexibility, and Neck Pain and just deploy it to Android. 
- For each label can be download in [Convert TfLite](https://github.com/YogaLyze/yogalyze-machine-learning-model/tree/main/Convert%20TfLite) folder and download [Convert.ipynb](https://github.com/YogaLyze/yogalyze-machine-learning-model/blob/main/Convert%20TfLite/Convert.ipynb) Folder.
- If you want try the model on android, you can clone [Test Deploy](https://github.com/YogaLyze/yogalyze-machine-learning-model/tree/main/Test%20Deploy) on Android Studio and run it.

